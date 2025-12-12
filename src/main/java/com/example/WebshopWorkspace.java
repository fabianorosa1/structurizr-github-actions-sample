package com.example;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.*;
import com.structurizr.view.AutomaticLayout.RankDirection;

import java.io.File;
import java.util.UUID;

public class WebshopWorkspace {

    public static void main(String[] args) throws Exception {

        Workspace workspace = new Workspace("My Webshop Workspace", "Generated from DSL");
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        // ---------------------------------------------------------
        // THEMES & STYLES
        // ---------------------------------------------------------

        views.getConfiguration().setThemes(
                "https://fabianorosa1.github.io/structurizr-themes/sap-btp-solution-diagrams-2025.12.08/theme.json"
        );

        // Style override for Database shape (applies to elements tagged "Database")
        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle("Database").shape(Shape.Cylinder);
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        // ---------------------------------------------------------
        // MODEL
        // ---------------------------------------------------------

        // People
        Person customer = model.addPerson("Customer", "The customer of our webshop");
        Person administrator = model.addPerson("Administrator", "The administrator of the webshop");

        // External system
        SoftwareSystem globalPayment = model.addSoftwareSystem(
                "Global Payment",
                "Used for all banking transactions"
        );

        // Software System: My Webshop
        SoftwareSystem myWebshop = model.addSoftwareSystem(
                "My Webshop",
                "Our beautiful webshop"
        );
        myWebshop.addTags("SAP L0");

        // Containers
        Container customerFrontend = myWebshop.addContainer(
                "customerFrontend",
                "The frontend for the customer",
                ""
        );

        Container administratorFrontend = myWebshop.addContainer(
                "administratorFrontend",
                "The frontend for the administrator",
                ""
        );

        Container webshopBackend = myWebshop.addContainer(
                "webshopBackend",
                "The webshop backend",
                ""
        );

        Container webshopDatabase = myWebshop.addContainer(
                "webshopDatabase",
                "The webshop database",
                ""
        );
        webshopDatabase.addTags("SAP L1");

        addDatabases(myWebshop, webshopBackend);

        // ---------------------------------------------------------
        // RELATIONSHIPS
        // ---------------------------------------------------------

        // System context
        customer.uses(myWebshop, "Uses");
        administrator.uses(myWebshop, "Uses");
        myWebshop.uses(globalPayment, "Uses");

        // Software system (container-level) relationships
        customer.uses(customerFrontend, "Uses", "https");
        administrator.uses(administratorFrontend, "Uses", "https");
        customerFrontend.uses(webshopBackend, "Uses", "http");
        administratorFrontend.uses(webshopBackend, "Uses", "http");
        webshopBackend.uses(webshopDatabase, "Uses", "ODBC");
        webshopBackend.uses(globalPayment, "Uses", "https");

        // ---------------------------------------------------------
        // VIEWS
        // ---------------------------------------------------------

        // System Context View
        SystemContextView contextView = views.createSystemContextView(
                myWebshop,
                "MyWebshopSystemContextView",
                "System Context for My Webshop"
        );
        contextView.addAllElements();
        contextView.enableAutomaticLayout(RankDirection.TopBottom);

        // Container View
        ContainerView containerView = views.createContainerView(
                myWebshop,
                "MyWebshopSoftwareSystemView",
                "Container View for My Webshop"
        );
        containerView.addAllElements();
        containerView.enableAutomaticLayout(RankDirection.TopBottom);

        // ---------------------------------------------------------
        // SAVE WORKSPACE
        // ---------------------------------------------------------

        com.structurizr.util.WorkspaceUtils.saveWorkspaceToJson(
                workspace,
                new File("workspace.json")
        );
    }

    public static void addDatabases(SoftwareSystem myWebshop, Container webshopBackend) {
        for (int i = 0; i < 10; i++) {
                Container database = myWebshop.addContainer(
                        "database-" + UUID.randomUUID().toString(),
                        "The webshop database",
                        ""
                );
                database.addTags("SAP L1");    
                webshopBackend.uses(database, "Uses", "JDBC");            
        }
    }
}