/**
 * Author: Bibrak Qamar Chandio
 * 		   PhD Student Indiana University, Bloomington, IN
 *		   10-Dec-2016
 * Summary:
 * 	 Manager for EdgeDetectionSolution. All IO is between the CPU and the DFE.
 */
package edgedetection;

import com.maxeler.maxcompiler.v2.build.EngineParameters;
import com.maxeler.maxcompiler.v2.kernelcompiler.Kernel;
import com.maxeler.maxcompiler.v2.managers.engine_interfaces.CPUTypes;
import com.maxeler.maxcompiler.v2.managers.engine_interfaces.EngineInterface;
import com.maxeler.maxcompiler.v2.managers.engine_interfaces.InterfaceParam;
import com.maxeler.maxcompiler.v2.managers.standard.Manager;
import com.maxeler.maxcompiler.v2.managers.standard.Manager.IOType;

public class EdgeDetectionSolutionManager {

	public static void main(String[] args) {
		EngineParameters params = new EngineParameters(args);
		Manager manager   = new Manager(params);
		Kernel  kernel    = new EdgeDetectionSolutionKernel(manager.makeKernelParameters());
		manager.setKernel(kernel);
		manager.setIO(IOType.ALL_CPU);
		manager.addInterface(modeDefault());
		manager.build();
	}

	static EngineInterface modeDefault() {
		EngineInterface m = new EngineInterface();
		InterfaceParam size = m.addParam("size", CPUTypes.INT32);
		m.setTicks("EdgeDetectionSolutionKernel", size);
		m.setStream("inImage", CPUTypes.INT32, size * CPUTypes.INT32.sizeInBytes());
		m.setStream("outImage", CPUTypes.INT32, size * CPUTypes.INT32.sizeInBytes());
		return m;
	}
}
