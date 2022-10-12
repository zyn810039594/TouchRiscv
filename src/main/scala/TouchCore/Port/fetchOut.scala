package TouchCore.Port

import TouchCore.BaseType.TouchBundle
import spinal.core._
import spinal.lib.IMasterSlave

case class fetchOut() extends TouchBundle{
	val pc = UInt(iAddrWidth bits)
	val inst = Bits(iDataWidth bits)
}
