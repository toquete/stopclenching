import SwiftUI
import MultiPlatformLibrary

@main
struct iOSApp: App {
    
    init() {
        KoinHelperKt.doInitKoin()
        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .badge, .sound]) { success, error in
            
        }
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
