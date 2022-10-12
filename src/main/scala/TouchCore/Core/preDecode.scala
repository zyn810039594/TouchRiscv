package TouchCore.Core

import TouchCore.BaseType.TouchModule
import spinal.core._
import spinal.lib._

class preDecode extends TouchModule{
	val io = new Bundle{
		val inst_in = slave(Flow(UInt(iAddrWidth bits)))

	}
}
