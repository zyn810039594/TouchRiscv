package TouchCore.ALU

import TouchCore.BaseType.TouchModule
import TouchCore.ALU.aluShiftFunc._
import spinal.core._
import spinal.lib._

class aluShift (num_width:Int=32) extends TouchModule{
	val io = new Bundle{
		val func = in(aluShiftFunc())
		val enable = in Bool()
		val num_A = in SInt (num_width bits)
		val num_B = in UInt (log2Up(num_width) bits)
		val num_out = out SInt (num_width bits)
	}
	when(io.enable){
		switch(io.func) {
			is(f_sll) {
				io.num_out := io.num_A |<< io.num_B
			}
			is(f_srl) {
				io.num_out := io.num_A |>> io.num_B
			}
			is(f_sla) {
				io.num_out := io.num_A << io.num_B
			}
			is(f_sra) {
				io.num_out := io.num_A >> io.num_B
			}
			default {
				io.num_out := S(0)
			}
		}
	}otherwise{
		io.num_out := S(0)
	}

}
