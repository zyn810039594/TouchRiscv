package TouchCore.Core

import TouchCore.BaseType.TouchModule
import TouchCore.Riscv.{const, rv32i, rvc}
import spinal.core._
import spinal.lib._

class pcGenerator extends TouchModule{
	val io = new Bundle{
		val pc_change = slave(Flow(UInt(iAddrWidth bits)))
		val pc_next = out UInt(iAddrWidth bits)
		val pc_current = in UInt(iAddrWidth bits)
		val inst = in Bits(iDataWidth bits)
	}
	val pred_offset = SInt(iAddrWidth+1 bits)
	val buildin_pc_next = io.pc_current.resize(iAddrWidth+1)+pred_offset.asUInt
	def rvi_inst:Bits = io.inst
	val rvi_j = io.inst === rv32i.JAL()
	val rvi_bj =
		(rvi_inst === rv32i.BEQ())   |
		(rvi_inst === rv32i.BLT())   |
		(rvi_inst === rv32i.BGE())   |
		(rvi_inst === rv32i.BLTU())  |
		(rvi_inst === rv32i.BGEU())
	if(rvcEnabled) {
		def rvc_inst:Bits = rvi_inst(15 downto 0)
		val rvi_hit = rvcModule.rvcCheck()(io.inst)
		val rvc_j = (rvc_inst===rvc.JAL())|(rvc_inst===rvc.J())
		val rvc_bj = (rvc_inst===rvc.BEQZ())|(rvc_inst===rvc.BNEZ())
		switch(rvi_hit##rvi_j##rvi_bj##rvc_j##rvc_bj){
			is(M"110--"){
				pred_offset:=rv32i.JAL.imm(rvi_inst).resized
			}
			is(M"101--"){
				when(rv32i.BGE.imm(rvi_inst).sign){
					pred_offset:=S(4).resized
				}otherwise{
					pred_offset:=rv32i.BGE.imm(rvi_inst).resized
				}
			}
			is(M"0--10"){
				pred_offset:=const.rvcIMM(rvc_inst).JAL.resized
			}
			is(M"0--01"){
				when(const.rvcIMM(rvc_inst).BEQZ.sign){
					pred_offset:=S(4).resized
				}otherwise{
					pred_offset:=const.rvcIMM(rvc_inst).BEQZ.sign.resized
				}
			}
			default{
				pred_offset:=S(4).resized
			}
		}
	}
	else{
		switch(rvi_j##rvi_bj){
			is(B"10"){
				pred_offset:=rv32i.JAL.imm(rvi_inst).resized
			}
			is(B"01"){
				when(rv32i.BGE.imm(rvi_inst).sign) {
					pred_offset := S(4).resized
				} otherwise {
					pred_offset := rv32i.BGE.imm(rvi_inst).resized
				}
			}
			default{
				pred_offset := S(4).resized
			}
		}
	}




}
