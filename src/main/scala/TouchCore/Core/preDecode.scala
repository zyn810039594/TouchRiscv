package TouchCore.Core

import TouchCore.BaseType.TouchModule
import TouchCore.Port.preDecodeInfo
import TouchCore.Riscv.rv32i._
import TouchCore.Riscv.rvInst
import TouchCore.Riscv.rvInst.instPreDecodeType._
import spinal.core._
import spinal.lib._

class preDecode extends TouchModule{
	val io = new Bundle{
		val inst_in = slave(Flow(UInt(iAddrWidth bits)))
		val inst_out = master(Stream(preDecodeInfo()))
	}
	private def out_info = io.inst_out.payload
	private def op_cut(opcode:Bits) = opcode(6 downto 2)
	switch(io.inst_in.payload(6 downto 2)){
		is(op_cut(opcode_J)) {
			out_info.i_type:=J_Type
		}
		is(op_cut(opcode_IC)){
			out_info.i_type:=IC_Type
		}
		is(op_cut(opcode_IL)){
			out_info.i_type:=IL_Type
		}
		is(op_cut(opcode_IJ)){
			out_info.i_type:=IJ_Type
		}
		is(op_cut(opcode_B)){
			out_info.i_type:=B_Type
		}
		is(op_cut(opcode_UA)){
			out_info.i_type:=UA_Type
		}
		is(op_cut(opcode_UL)){
			out_info.i_type:=UL_Type
		}
		is(op_cut(opcode_S)){
			out_info.i_type:=S_Type
		}

	}
}
