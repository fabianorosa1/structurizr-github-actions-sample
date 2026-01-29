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

        // Software System: Cloud Identity Services - INO
        SoftwareSystem cis = model.addSoftwareSystem(
                "SAP Cloud Identity Services - INO",
                "Used for IAM for SAP solutions"
        );
        cis.addTags("SAP CIS L0");

        // Containers - CIS Applications
        Container cisIasApplicationsContainer = cis.addContainer(
                "IAS",
                "Identity Authentication Service",
                "Applications/Endpoints"
        );
        cisIasApplicationsContainer.addTags("SAP IAS L1");

        // External systems
        SoftwareSystem corporateIdp = model.addSoftwareSystem(
                "PingID - DEV",
                "Used for IAM for corporate users"
        );
        corporateIdp.addTags("PING ID L0");
        
        SoftwareSystem btpCF = model.addSoftwareSystem(
                "SAP Business Technology Platform - CF",
                "Used for data, extension and integration"
        );
        btpCF.addTags("SAP BTP CF L0");

        SoftwareSystem btpNeo = model.addSoftwareSystem(
                "SAP Business Technology Platform - Neo",
                "Used for data, extension and integration"
        );
        btpNeo.addTags("SAP BTP NEO L0");

        SoftwareSystem s4hanaOnPrem = model.addSoftwareSystem(
                "S/4 HANA OnPrem",
                "Used for ERP core processes"
        );
        s4hanaOnPrem.addTags("SAP L0");
        
        Container s4hanaOnPremiseADCContainer = s4hanaOnPrem.addContainer(
                "S/4 HANA OnPrem ADC",
                "S/4 HANA OnPrem ADC",
                "System"
        );
        s4hanaOnPremiseADCContainer.addTags("SAP L0");

        Container s4hanaOnPremiseCfinContainer = s4hanaOnPrem.addContainer(
                "S/4 HANA OnPrem CFIN",
                "S/4 HANA OnPrem CFIN",
                "System"
        );
        s4hanaOnPremiseCfinContainer.addTags("SAP L0");

        Container s4hanaOnPremiseNucleosContainer = s4hanaOnPrem.addContainer(
                "S/4 HANA OnPrem Nucleos",
                "S/4 HANA OnPrem Nucleos",
                "System"
        );    
        s4hanaOnPremiseNucleosContainer.addTags("SAP L0");

        // Containers - CIS Applications
        Container CISA_BTPCF_INO_SHELL_CF_OPEX_EU20 = cis.addContainer(
                "CISA_BTPCF_INO_SHELL_CF_OPEX_EU20",
                "CISA_BTPCF_INO_SHELL_CF_OPEX_EU20",
                "Application Configuration"
        );
        
        Container CISA_BTPCF_INO_SHELL_CF_AFC_EU10 = cis.addContainer(
                "CISA_BTPCF_INO_SHELL_CF_AFC_EU10",
                "CISA_BTPCF_INO_SHELL_CF_AFC_EU10",
                "Application Configuration"
        );    

        Container CISA_BTPNEO_INO_GF_SERP_IG_001 = cis.addContainer(
                "CISA_BTPNEO_INO_GF_SERP_IG_001",
                "CISA_BTPNEO_INO_GF_SERP_IG_001",
                "Application Configuration"
        );

        Container CISA_S4HANA_ADC_S59_100 = cis.addContainer(
                "CISA_S4HANA_ADC_S59_100",
                "CISA_S4HANA_ADC_S59_100",
                "Application Configuration"
        );
        
        //addDatabases(myWebshop, webshopBackend);

        // ---------------------------------------------------------
        // RELATIONSHIPS
        // ---------------------------------------------------------

        // System Landscape context        
        btpCF.uses(cis, "Authenticate");
        btpNeo.uses(cis, "Authenticate");
        s4hanaOnPrem.uses(cis, "Authenticate");
        cis.uses(corporateIdp, "Trust");
        
        // Software system (container-level) relationships
        CISA_BTPCF_INO_SHELL_CF_AFC_EU10.uses(cisIasApplicationsContainer, "Authenticate", "SAML2.0/OIDC");
        CISA_BTPCF_INO_SHELL_CF_OPEX_EU20.uses(cisIasApplicationsContainer, "Authenticate", "SAML2.0/OIDC");
        CISA_BTPNEO_INO_GF_SERP_IG_001.uses(cisIasApplicationsContainer, "Authenticate", "SAML2.0/OIDC");
        CISA_S4HANA_ADC_S59_100.uses(cisIasApplicationsContainer, "Authenticate", "SAML2.0/OIDC");

        s4hanaOnPrem.uses(CISA_S4HANA_ADC_S59_100, "Authenticate");
        s4hanaOnPrem.uses(CISA_BTPNEO_INO_GF_SERP_IG_001, "Authenticate");
        s4hanaOnPrem.uses(CISA_BTPCF_INO_SHELL_CF_AFC_EU10, "Authenticate");

        s4hanaOnPremiseADCContainer.uses(CISA_S4HANA_ADC_S59_100, "Authenticate", "SAML2.0/OIDC");
        s4hanaOnPremiseCfinContainer.uses(CISA_BTPNEO_INO_GF_SERP_IG_001, "Authenticate", "SAML2.0/OIDC");
        s4hanaOnPremiseNucleosContainer.uses(CISA_BTPCF_INO_SHELL_CF_AFC_EU10, "Authenticate", "SAML2.0/OIDC");
            
        cisIasApplicationsContainer.uses(corporateIdp, "Trust");
        
        // ---------------------------------------------------------
        // VIEWS
        // ---------------------------------------------------------

        // System Landscape View View CIS
        SystemLandscapeView  systemLandscapeViewCis = views.createSystemLandscapeView (
                "CisSystemLandscapeView",
                "System Landscape View for CIS"
        );
        systemLandscapeViewCis.addAllElements();
        systemLandscapeViewCis.enableAutomaticLayout(RankDirection.BottomTop);

        // Container View CIS
        ContainerView containerViewCis = views.createContainerView(
                cis,
                "CisSoftwareSystemView",
                "Container View for CIS"
        );
        containerViewCis.addAllElements();
        containerViewCis.enableAutomaticLayout(RankDirection.BottomTop);

        // Container View S4
        ContainerView containerViewS4 = views.createContainerView(
                s4hanaOnPrem,
                "S4SoftwareSystemView",
                "Container View for S4"
        );

        //containerViewS4.addNearestNeighbours(cisIasApplicationsContainer);
        //containerViewS4.addAllContainersAndInfluencers();
        containerViewS4.add(CISA_S4HANA_ADC_S59_100);        
        containerViewS4.add(CISA_S4HANA_ADC_S59_100);
        containerViewS4.add(CISA_BTPNEO_INO_GF_SERP_IG_001);
        containerViewS4.add(CISA_BTPCF_INO_SHELL_CF_AFC_EU10);
        containerViewS4.add(s4hanaOnPremiseADCContainer);
        containerViewS4.add(s4hanaOnPremiseCfinContainer);
        containerViewS4.add(s4hanaOnPremiseNucleosContainer);        
        containerViewS4.add(cisIasApplicationsContainer);
        containerViewS4.add(corporateIdp);
        
        containerViewS4.enableAutomaticLayout(RankDirection.BottomTop);
        
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
