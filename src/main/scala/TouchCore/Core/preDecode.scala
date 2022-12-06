package TouchCore.Core

import TouchCore.BaseType.TouchModule
import spinal.core._
import spinal.lib._
class preDecode extends TouchModule {
	val io = new Bundle{
		val inst = slave(Stream(Bits(iDataWidth bits)))
		val reg_read_id = Vec(master(Flow(UInt(if(rviEnabled) 5 bits else 4 bits))),2)
	}
}
