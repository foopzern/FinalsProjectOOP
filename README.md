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


Date: June 11 2026
Role: JavaFX Frontend 2
What: Admin finalization

Assets for Admin ( done )
- No match suggestion icon for admin sidebar navigation
- No finditems, LostReports logo for admin dashboard

Part 1: Architectural Achievements & UI/UX Milestones
The Admin portal is now structurally complete and fully interactive. By refactoring the codebase, we have established a highly scalable desktop application standard.

Component-Based UI: The sidebar was successfully decoupled into its own master component (AdminSidebarController and AdminDashboardSidebar.fxml). By using <fx:include>, the application now injects the sidebar into every page. This eliminated hundreds of lines of duplicate code and ensures that any future changes to the navigation only need to be made in one file.

Root-Swapping Navigation: The application now uses setRoot() instead of new Scene() for routing. This locks the application canvas at a stable 1000x600 resolution, entirely eliminating the jarring window resizing and flickering that happens when switching between the Dashboard, Claims, and Reported Items.

Dynamic Visual States: * Implemented a fully collapsible "hamburger" drawer menu that smoothly transitions from a 220px expanded state to a 75px icon-only state using ContentDisplay.

Engineered a dynamic image-loader that utilizes JavaFX's ColorAdjust to turn native black icons white on dark backgrounds, while stripping the effect away to highlight the active tab in vibrant yellow.

Advanced Layering (Modals): Successfully implemented dual-layer architecture in the Match Suggestions view. The main panel utilizes a ScrollPane for dynamic cards, while the "View Match Details" button launches a focused, centered modal overlay that blocks background interaction (Modality.APPLICATION_MODAL).

Part 2: The Backend & Database Integration Roadmap
Right now, the application is functioning perfectly in "Mock Data" mode. The Java controllers are populating the UI using hardcoded ObservableList data.

When you transition to writing the backend logic and hooking up your database, here is the exact blueprint of what needs to change inside your controllers:

1. Authentication (AdminLoginController)

Current State: The login checks a hardcoded static string (if (entered.equals("admin123"))).

The Backend Change: You will need to write a JDBC query that searches your Admins table for a matching username/ID. Crucially, never store or check passwords in plain text. You will want to implement a hashing library (like BCrypt) to verify the entered password against the hashed string in your database.

2. Dashboard Analytics (AdminDashboardController)

Current State: The stat labels ("12 Found", "8 Lost") and the progress bars are manually typed strings and hardcoded decimal values.

The Backend Change: This is where you will heavily utilize SQL aggregate functions. You will write queries using COUNT(), GROUP BY, and potentially roll-up summaries to count how many items exist in the database grouped by category (Electronics, Wallet, Document). Your Java code will then use those exact integers to dynamically calculate the setProgress() decimals for the UI.

3. Populating Tables & Cards (Claims, Reported Items, Match Panel)

Current State: The masterData.addAll(...) methods are manually creating ClaimRow and ReportedItem objects to make the tables look full.

The Backend Change: You should implement the DAO (Data Access Object) Pattern. Create a class like ItemDAO with a method called getAllReportedItems().

This method will execute a SELECT * FROM items query.

It will loop through the Java ResultSet, creating a new Java object for every row in the database, and return a complete List.

Your controllers will simply call masterData.addAll(ItemDAO.getAllReportedItems());.

4. Action Handlers (Approve, Reject, Confirm Match)

Current State: Clicking "Approve" on a claim updates the Java object's string to "Approved" and refreshes the table visually.

The Backend Change: These buttons must trigger UPDATE queries. When an admin clicks "Approve", the controller needs to grab the unique SQL Primary Key (ID) of that specific claim. It will then pass that ID to a backend method that executes: UPDATE claims SET status = 'Approved' WHERE claim_id = ?. Once the database confirms the update was successful, then you refresh the JavaFX table.