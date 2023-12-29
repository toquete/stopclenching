import SwiftUI
import MultiPlatformLibrary
import mokoMvvmFlowSwiftUI

struct ContentView: View {
    @State private var initialTime = "08:00"
    @State private var finalTime = "17:00"
    @State private var interval = "3600000"
    @ObservedObject var viewModel: MainViewModel = MainHelper().getMainViewModel()

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
                    viewModel.onScheduleAlarmClick(alarmItem: AlarmItem(from: initialTime, to: finalTime, intervalMillis: Int32(interval) ?? 0))
                }, label: {
                    Text("Schedule")
                })
                Button(action: { 
                    viewModel.onCancelAlarmClick(alarmItem: AlarmItem(from: initialTime, to: finalTime, intervalMillis: Int32(interval) ?? 0))
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
