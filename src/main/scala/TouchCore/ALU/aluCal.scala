package TouchCore.ALU

import TouchCore.BaseType.TouchModule
import TouchCore.ALU.aluCalFunc._
import spinal.core._
import spinal.lib._

class aluCal (num_width:Int=32) extends TouchModule{
	val io = new Bundle{
		val func = in(aluCalFunc())
		val enable = in Bool()
		val num_A = in SInt(num_width bits)
		val num_B = in SInt(num_width bits)
		val num_out = out SInt(num_width bits)
		val zero = out Bool()
	}
	val sub_num1 = SInt(num_width bits)
	val sub_num2 = SInt(num_width bits)
	val sub_mod = sub_num1-sub_num2

	val num_A_sign = io.num_A.sign
	val num_B_sign = io.num_B.sign
	when(io.enable) {
		io.zero := io.num_out.orR
		sub_num1 := io.num_A
		sub_num2 := io.num_B
		switch(io.func) {
			is(f_and) {
				io.num_out := io.num_A & io.num_B
			}
			is(f_or) {
				io.num_out := io.num_A | io.num_B
			}
			is(f_plus) {
				io.num_out := io.num_A + io.num_B
			}
			is(f_sub) {
				io.num_out := sub_mod
			}
			is(f_comp){
				when(num_A_sign&(!num_B_sign)){
					io.num_out := S(0)
				}elsewhen((!num_A_sign)&num_B_sign){
					io.num_out := S(1)
				}otherwise{
					io.num_out := (!sub_mod.sign)
				}
			}
			is(f_compu) {
				io.num_out := (io.num_A < io.num_B)?S(1)|S(0)
			}
			is(f_xor) {
				io.num_out := io.num_A ^ io.num_B
			}
			default {
				io.num_out := S(0)
			}
		}
	}otherwise{
		sub_num1 := S(0)
		sub_num2 := S(0)
		io.num_out:=S(0)
		io.zero:=False
	}


}
