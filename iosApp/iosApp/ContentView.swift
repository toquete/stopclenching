import SwiftUI
import MultiPlatformLibrary

struct ContentView: View {
    @State private var initialTime = "08:00"
    @State private var finalTime = "17:00"
    @State private var interval = "3600000"
    
    private let scheduler = IOSAlarmScheduler()

	var body: some View {
        VStack {
            TextField(text: $initialTime, prompt: Text("Initial time")) {
                Text("Initial time")
            }
            TextField(text: $finalTime, prompt: Text("Final time")) {
                Text("Final time")
            }
            TextField(text: $interval, prompt: Text("Interval")) {
                Text("Interval")
            }
            HStack {
                Button(action: {
                    scheduler.schedule(item: AlarmItem(from: initialTime, to: finalTime, intervalMillis: Int64(interval) ?? 0))
                }, label: {
                    Text("Schedule")
                })
                Button(action: { scheduler.cancel(item: AlarmItem(from: initialTime, to: finalTime, intervalMillis: Int64(interval) ?? 0))
                }, label: {
                    Text("Cancel")
                })
            }
            .buttonStyle(.borderedProminent)
        }
        .padding()
        .textFieldStyle(.roundedBorder)
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
