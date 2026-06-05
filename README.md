# For Reference:
*Design:* [here](https://www.canva.com/design/DAHKKY9_cxc/gAYc7tdZX2NO6VGf4H8ozw/edit)

# Frontend Status Report 
**Date:** May 28, 2026  
**Role:** Frontend Developer - JAVA FX & CONTROLLERS 

## Completed & Functional
* **Main Portal:** Fully functional. `MainPortalController` is wired up and routing correctly.
* **Admin Login:** UI is complete and controller is linked. "Back" and "Cancel" buttons successfully route back to the Main Portal. *(Note for Backend: "Login" button is wired and currently awaits JDBC database integration).*

## Work In Progress (WIP)
* **User Dashboard:** Layout is structured using `ImageView` components. Pending controller creation and sidebar routing logic.
* **Help Screen:** Base layout is complete. Currently resolving minor alignment/rendering issues with the `ImageView` components.
* **Ready to Claim Form (`ClaimFormReady.fxml`):** FXML export was cut off midway. Currently manually patching and restructuring the layout code.

## UI/UX Asset Requests
**@UI/UX Team:** Please review the following asset issues so we can finalize the frontend screens:

**Missing Assets:**
* [ ] `Items` Icon

**Asset Revisions Needed (Recoloring/Formatting):**
* [ ] `Dashboard` Icon (Require a Yellow variant for the active sidebar state)
* [ ] `Item` Icon (Require a Yellow variant)
* [ ] `Claims` Icon (Require a Yellow variant)
* [ ] `Help` Icon (Require a Yellow variant)
* [ ] `Search`, `Claim`, and `Report` Icons for the Help Page (Require background removal/transparency and recolored to Maroon)

# Frontend Status Report 
**Date:** June 5, 2026
**Role:** Frontend Developer - JAVA FX & CONTROLLERS 

## Completed & Functional
* **Master Sidebar Architecture (`DashboardSidebar.fxml`):** Successfully separated the sidebar into its own master FXML file to resolve layout rendering bugs. It is now dynamically injected into other screens using `<fx:include>`.
* **Dashboard Navigation Flow:** The Dashboard and Help screens are fully functional and routing seamlessly. Center dashboard buttons (Report Lost, Report Found, Filter) are successfully wired to the controller.

## Work In Progress (WIP)
* **Secondary Screens:** Currently have placeholder actions set up for Items Gallery (`Items.fxml`), Claims Page (`Claims.fxml`), Lost Form (`LostForm.fxml`), and Found Form (`FoundForm.fxml`). Awaiting final FXML layouts to complete the routing.

## UI/UX Asset Requests
**@UI/UX Team:** Please review the following asset issues so we can finalize the frontend screens:

**Asset Revisions Needed (Recoloring/Formatting):**
* [ ] **Yellow Sidebar Icons:** I have successfully coded the sidebar text and borders to turn yellow when active, but I cannot change the color of the physical `.png` files via code. I need yellow variants of the following icons exported so I can dynamically swap them when a tab is clicked:
    * Dashboard Icon (`dashboard.png`)
    * Items Icon (`items.png`)
    * Claims Icon (`reportSidebar.png`)
    * Help Icon (`help.png`)