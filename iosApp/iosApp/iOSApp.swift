import SwiftUI

@main
struct iOSApp: App {
    
    init() {
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { success, error in
            
        }
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
