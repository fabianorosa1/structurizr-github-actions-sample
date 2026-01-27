package com.example;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.util.WorkspaceUtils;
import com.structurizr.view.*;
import com.structurizr.view.AutomaticLayout.RankDirection;

import java.io.File;

public class StructurizrWorkspace {

    public static void main(String[] args) throws Exception {
        Workspace workspace = new Workspace("Big Bank plc", "Internet Banking System");
        Model model = workspace.getModel();
        ViewSet views = workspace.getViews();

        views.getConfiguration().setThemes(
                "https://fabianorosa1.github.io/structurizr-themes/sap-btp-solution-diagrams-2025.12.08/theme.json");

        Styles styles = views.getConfiguration().getStyles();
       // styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
       // styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        Person customer = model.addPerson("Customer", "A customer of the bank.");
        SoftwareSystem internetBankingSystem = model.addSoftwareSystem("Internet Banking System",
                "Allows customers to view accounts and make payments.");
        customer.uses(internetBankingSystem, "Uses");

        Container webApp = internetBankingSystem.addContainer("Web Application", "Delivers the web UI.",
                "Java & Spring MVC");
        Container database = internetBankingSystem.addContainer("Database", "Stores customer data.", "Oracle");
        webApp.addTags("SAP L1");

        customer.uses(webApp, "Uses");
        webApp.uses(database, "Reads/Writes");

        DeploymentNode liveWebServer = model.addDeploymentNode(
                "Live", // ← environment
                "bigbank-web***",
                "A web server in the farm.",
                "Ubuntu 16.04 LTS",
                8);
        DeploymentNode tomcat = liveWebServer.addDeploymentNode(
                "Apache Tomcat",
                "Java web server.",
                "Tomcat 8.x",
                1);

        tomcat.add(webApp);
        tomcat.addTags("SAP L0");

        DeploymentNode primaryDbServer = model.addDeploymentNode(
                "Live",
                "bigbank-db01",
                "Primary DB server.",
                "Ubuntu 16.04 LTS",
                1);
        DeploymentNode oraclePrimary = primaryDbServer.addDeploymentNode(
                "Oracle - Primary",
                "Live database.",
                "Oracle 12c");
        oraclePrimary.add(database);

        DeploymentNode secondaryDbServer = model.addDeploymentNode(
                "Live",
                "bigbank-db02",
                "Secondary DB server.",
                "Ubuntu 16.04 LTS",
                1);
        secondaryDbServer.addDeploymentNode(
                "Oracle - Secondary",
                "Standby database.",
                "Oracle 12c");

        DeploymentView liveDeployment = views.createDeploymentView(internetBankingSystem, "LiveDeployment",
                "Live environment deployment");
        liveDeployment.setEnvironment("Live");
        liveDeployment.addAllDeploymentNodes();
        liveDeployment.enableAutomaticLayout(RankDirection.TopBottom);

        WorkspaceUtils.saveWorkspaceToJson(workspace, new File("workspace.json"));
    }
}
