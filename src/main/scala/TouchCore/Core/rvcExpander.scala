package TouchCore.Core

import TouchCore.BaseType._
import TouchCore.Riscv._
import TouchCore.Riscv.const._
import spinal.core._

/**
 * RVC instruction set expander, which changes RVC instruction to standard RV32 instruction
 *
 * @param cmd Bits-type 16-bits RVC instruction
 * @return Bit-type 32-bits RV32 instruction type
 */
case class rvcExpander(rvc_inst:Bits) extends TouchArea{
	private def err_code = B"32'hFFFFFFFF"
	def apply: Bits = {
		val rv32_inst = Bits(32 bits)
		val imm = const.rvcIMM(rvc_inst)
		switch(rvc_inst) {
			is(rvc.ADDI4SPN) {
				rv32_inst := rv32i.ADDI.encode(
					i_rd = rvc.ADDI4SPN.rd_value(rvc_inst),
					i_rs1 = U(2),
					i_imm = imm.ADDI4SPN
				)
			}
			is(rvc.LW) {
				rv32_inst := rv32i.LW.encode(
					i_rd = rvc.LW.rd_value(rvc_inst),
					i_rs1 = rvc.LW.rs1_value(rvc_inst),
					i_imm = imm.LW
				)
			}
			is(rvc.SW) {
				rv32_inst := rv32i.SW.encode(
					i_rs1 = rvc.SW.rs1_value(rvc_inst),
					i_rs2 = rvc.SW.rs2_value(rvc_inst),
					i_imm = imm.SW
				)
			}
			is(rvc.ADDI) {
				rv32_inst := rv32i.ADDI.encode(
					i_rd = rvc.ADDI.rd_value(rvc_inst),
					i_rs1 = rvc.ADDI.rs1_value(rvc_inst),
					i_imm = imm.ADDI
				)
			}
			is(rvc.JAL) {
				rv32_inst := rv32i.JAL.encode(
					i_rd = U(1),
					i_imm = imm.JAL
				)
			}
			is(rvc.LI) {
				when(rvc_inst(CI_REG.rd) === B(0)) {
					rv32_inst := err_code
				} otherwise {
					rv32_inst := rv32i.ADDI.encode(
						i_rd = rvc.LI.rd_value(rvc_inst),
						i_rs1 = U(0),
						i_imm = imm.LI
					)
				}
			}
			is(rvc.ADDI16SP) {
				switch(rvc_inst(CI_REG.rd)) {
					is(B(2)) {
						rv32_inst := rv32i.ADDI.encode(
							i_rd = U(2),
							i_rs1 = U(2),
							i_imm = imm.ADDI16SP
						)
					}
					is(B(0)) {
						rv32_inst := err_code
					}
					default {
						rv32_inst := rv32i.LUI.encode(
							i_rd = rvc.ADDI16SP.rd_value(rvc_inst),
							i_imm = imm.LUI
						)
					}
				}
			}
			is(rvc.SRLI) {
				rv32_inst := rv32i.SRLI.encode(
					i_rd = rvc.SRLI.rd_value(rvc_inst),
					i_rs1 = rvc.SRLI.rs1_value(rvc_inst),
					i_imm = imm.SRLI
				)
			}
			is(rvc.SRAI) {
				rv32_inst := rv32i.SRLI.encode(
					i_rd = rvc.SRAI.rd_value(rvc_inst),
					i_rs1 = rvc.SRAI.rs1_value(rvc_inst),
					i_imm = imm.SRAI
				)
			}
			is(rvc.ANDI) {
				rv32_inst := rv32i.ANDI.encode(
					i_rd = rvc.ANDI.rd_value(rvc_inst),
					i_rs1 = rvc.ANDI.rs1_value(rvc_inst),
					i_imm = imm.ANDI
				)
			}
			is(rvc.SUB) {
				rv32_inst := rv32i.SUB.encode(
					i_rd = rvc.SUB.rd_value(rvc_inst),
					i_rs1 = rvc.SUB.rs1_value(rvc_inst),
					i_rs2 = rvc.SUB.rs2_value(rvc_inst)
				)
			}
			is(rvc.XOR) {
				rv32_inst := rv32i.XOR.encode(
					i_rd = rvc.XOR.rd_value(rvc_inst),
					i_rs1 = rvc.XOR.rs1_value(rvc_inst),
					i_rs2 = rvc.XOR.rs2_value(rvc_inst)
				)
			}
			is(rvc.OR) {
				rv32_inst := rv32i.OR.encode(
					i_rd = rvc.OR.rd_value(rvc_inst),
					i_rs1 = rvc.OR.rs1_value(rvc_inst),
					i_rs2 = rvc.OR.rs2_value(rvc_inst)
				)
			}
			is(rvc.AND) {
				rv32_inst := rv32i.AND.encode(
					i_rd = rvc.AND.rd_value(rvc_inst),
					i_rs1 = rvc.AND.rs1_value(rvc_inst),
					i_rs2 = rvc.AND.rs2_value(rvc_inst)
				)
			}
			is(rvc.J) {
				rv32_inst := rv32i.JAL.encode(
					i_rd = U(0),
					i_imm = imm.J
				)
			}
			is(rvc.BEQZ) {
				rv32_inst := rv32i.BEQ.encode(
					i_rs1 = rvc.BEQZ.rs1_value(rvc_inst),
					i_rs2 = U(0),
					i_imm = imm.BEQZ
				)
			}
			is(rvc.BNEZ) {
				rv32_inst := rv32i.BNE.encode(
					i_rs1 = rvc.BNEZ.rs1_value(rvc_inst),
					i_rs2 = U(0),
					i_imm = imm.BNEZ
				)
			}
			is(rvc.SLLI) {
				rv32_inst := rv32i.SLLI.encode(
					i_rd = rvc.SLLI.rd_value(rvc_inst),
					i_rs1 = rvc.SLLI.rs1_value(rvc_inst),
					i_imm = imm.SLLI
				)
			}
			is(rvc.LWSP) {
				rv32_inst := rv32i.LW.encode(
					i_rd = rvc.LW.rd_value(rvc_inst),
					i_rs1 = U(2),
					i_imm = imm.LWSP
				)
			}
			is(rvc.JR) {
				switch((rvc_inst(CR_REG.rd) === B(0)) ## (rvc_inst(CR_REG.rs2) === B(0))) {
					is(B"2'b10") {
						rv32_inst := rv32i.JALR.encode(
							i_rd = U(0),
							i_rs1 = rvc.JR.rs1_value(rvc_inst),
							i_imm = S(0)
						)
					}
					is(B"2'b11") {
						rv32_inst := rv32i.ADD.encode(
							i_rd = rvc.JR.rd_value(rvc_inst),
							i_rs1 = U(0),
							i_rs2 = rvc.JR.rs2_value(rvc_inst)
						)
					}
					default {
						rv32_inst := err_code
					}
				}
			}
			is(rvc.EBREAK) {
				switch((rvc_inst(CR_REG.rd) === B(0)) ## (rvc_inst(CR_REG.rs2) === B(0))) {
					is(B"2'b00") {
						rv32_inst := rv32i.EBREAK.encode(
							i_rd = U(0),
							i_rs1 = U(0),
							i_imm = S(1)
						)
					}
					is(B"2'b10") {
						rv32_inst := rv32i.JALR.encode(
							i_rd = U(1),
							i_rs1 = rvc.EBREAK.rs1_value(rvc_inst),
							i_imm = S(0)
						)
					}
					is(B"2'b11") {
						rv32_inst := rv32i.ADD.encode(
							i_rd = rvc.EBREAK.rd_value(rvc_inst),
							i_rs1 = rvc.EBREAK.rs1_value(rvc_inst),
							i_rs2 = rvc.EBREAK.rs2_value(rvc_inst)
						)
					}
					default {
						rv32_inst := err_code
					}
				}
			}
			is(rvc.SWSP) {
				rv32_inst := rv32i.SW.encode(
					i_rs1 = U(2),
					i_rs2 = rvc.EBREAK.rs2_value(rvc_inst),
					i_imm = imm.SWSP
				)
			}
			default {
				rv32_inst := err_code
			}
		}
		rv32_inst
	}
}
