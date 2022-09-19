package TouchCore.Core

import spinal.core._
import spinal.lib._

class rvcConvert extends Component{
	val io = new Bundle{
		val inst_in = slave(Stream(Bits(30 bits)))
		val inst_out = master(Stream(Bits(30 bits)))
	}


}
