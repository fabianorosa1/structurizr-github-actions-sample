package com.example;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.*;
import com.structurizr.view.AutomaticLayout.RankDirection;

import java.io.File;
import java.util.UUID;

public class SapCisApplicationsWorkspace {

    public static void main(String[] args) throws Exception {

        Workspace workspace = new Workspace("SAP CIS Applications Workspace", "Generated from Java");
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
        //styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        // ---------------------------------------------------------
        // MODEL
        // ---------------------------------------------------------

        // People
        //Person customer = model.addPerson("Customer", "The customer of our webshop");
        //Person administrator = model.addPerson("Administrator", "The administrator of the webshop");

        // External systems
        SoftwareSystem corporateIdp = model.addSoftwareSystem(
                "PingID - DEV",
                "Used for IAM for corporate users"
        );

        SoftwareSystem btpCF = model.addSoftwareSystem(
                "SAP Business Technology Platform - CF",
                "Used for data, extension and integration"
        );

        SoftwareSystem btpNeo = model.addSoftwareSystem(
                "SAP Business Technology Platform - Neo",
                "Used for data, extension and integration"
        );

        SoftwareSystem s4hanaOnPrem = model.addSoftwareSystem(
                "S/4 HANA OnPrem",
                "Used for ERP core processes"
        );

        // Software System: Cloud Identity Services - INO
        SoftwareSystem cis = model.addSoftwareSystem(
                "SAP Cloud Identity Services - INO",
                "Used for IAM for SAP solutions"
        );
        cis.addTags("SAP L0");

        // Containers - CIS Applications
        Container cisIasApplicationsContainer = cis.addContainer(
                "IAS",
                "Identity Authentication Service",
                "Applications"
        );

        // Containers - CIS Applications
        Container CISA_BTPCF_INO_SHELL_CF_AFC_EU10 = cis.addContainer(
                "CISA_BTPCF_INO_SHELL_CF_AFC_EU10",
                "CISA_BTPCF_INO_SHELL_CF_AFC_EU10",
                "Application"
        );
        CISA_BTPCF_INO_SHELL_CF_AFC_EU10.addTags("NON SAP L0");

        Container CISA_BTPCF_INO_SHELL_CF_OPEX_EU20 = cis.addContainer(
                "CISA_BTPCF_INO_SHELL_CF_OPEX_EU20",
                "CISA_BTPCF_INO_SHELL_CF_OPEX_EU20",
                "Application"
        );
        CISA_BTPCF_INO_SHELL_CF_OPEX_EU20.addTags("NON SAP L1");

        Container CISA_BTPNEO_INO_GF_SERP_IG_001 = cis.addContainer(
                "CISA_BTPNEO_INO_GF_SERP_IG_001",
                "CISA_BTPNEO_INO_GF_SERP_IG_001",
                "Application"
        );

        Container CISA_S4HANA_ADC_S59_100 = cis.addContainer(
                "CISA_S4HANA_ADC_S59_100",
                "CISA_S4HANA_ADC_S59_100",
                "Application"
        );
        CISA_S4HANA_ADC_S59_100.addTags("SAP L1");

        //addDatabases(myWebshop, webshopBackend);

        // ---------------------------------------------------------
        // RELATIONSHIPS
        // ---------------------------------------------------------

        // System context
        cis.uses(corporateIdp, "Trust");
        btpCF.uses(cis, "Authenticate");
        btpNeo.uses(cis, "Authenticate");
        s4hanaOnPrem.uses(cis, "Authenticate");

        // Software system (container-level) relationships
        CISA_BTPCF_INO_SHELL_CF_AFC_EU10.uses(cisIasApplicationsContainer, "Authenticate", "SAML2.0/OIDC");
        CISA_BTPCF_INO_SHELL_CF_OPEX_EU20.uses(cisIasApplicationsContainer, "Authenticate", "SAML2.0/OIDC");
        CISA_BTPNEO_INO_GF_SERP_IG_001.uses(cisIasApplicationsContainer, "Authenticate", "SAML2.0/OIDC");
        CISA_S4HANA_ADC_S59_100.uses(cisIasApplicationsContainer, "Authenticate", "SAML2.0/OIDC");

        // ---------------------------------------------------------
        // VIEWS
        // ---------------------------------------------------------

        // System Context View
        SystemContextView contextView = views.createSystemContextView(
                cis,
                "CisSystemContextView",
                "System Context for CIS"
        );
        contextView.addAllElements();
        contextView.enableAutomaticLayout(RankDirection.TopBottom);

        // Container View
        ContainerView containerView = views.createContainerView(
                cis,
                "CisSoftwareSystemView",
                "Container View for CIS"
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
                        "db-" + UUID.randomUUID().toString(),
                        "The webshop database",
                         "Database"
                );
                database.addTags("SAP L1");    
                webshopBackend.uses(database, "Uses", "JDBC");            
        }
    }
}
