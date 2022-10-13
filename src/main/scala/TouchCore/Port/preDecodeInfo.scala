package TouchCore.Port

import TouchCore.BaseType.TouchBundle
import TouchCore.Riscv.rvInst.instPreDecodeType
import spinal.core._
import spinal.lib._

case class preDecodeInfo() extends TouchBundle{
	val i_type = instPreDecodeType()
	val inst = Bits(iAddrWidth-2 bits)
}
