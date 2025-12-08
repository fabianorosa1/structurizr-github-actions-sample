workspace {

    model {
        customer = person "Customer" "The customer of our webshop"
        administrator = person "Administrator" "The administrator of the webshop"
        globalPayment = softwareSystem "Global Payment" "Used for all banking transactions"

        myWebshop = softwareSystem "My Webshop" "Our beautiful webshop" {
            customerFrontend = container customerFrontend "The frontend for the customer"
            administratorFrontend = container administratorFrontend "The frontend for the administrator"
            webshopBackend = container webshopBackend "The webshop backend"
            webshopDatabase = container webshopDatabase "The webshop database" {
                tags "SAP L0"
            }
        }

        // system context relationships
        customer -> myWebshop "Uses"
        administrator -> myWebshop "Uses"
        myWebshop -> globalPayment "Uses"

        // software system relationships
        customer -> customerFrontend "Uses" "https"
        administrator -> administratorFrontend "Uses" "https"
        customerFrontend -> webshopBackend "Uses" "http"
        administratorFrontend -> webshopBackend "Uses" "http"
        webshopBackend -> webshopDatabase "Uses" "ODBC"
        webshopBackend -> globalPayment "Uses" "https"

    }

    views {
        systemContext myWebshop "MyWebshopSystemContextView" {
            include *
            autolayout
        }
        container myWebshop "MyWebshopSoftwareSystemView" {
            include *
            autolayout
        }
        themes default https://fabianorosa1.github.io/structurizr-themes/sap-btp-solution-diagrams-2025.12.08/theme.json
        styles {
            element "Database" {
                shape Cylinder
            }
        }
    }

}