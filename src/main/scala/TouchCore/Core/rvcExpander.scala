package TouchCore.Core

import spinal.core._

/**
 * RVC instruction set expander, which changes RVC instruction to standard RV32 instruction
 *
 * @param cmd Bits-type 16-bits RVC instruction
 * @return Bit-type 32-bits RV32 instruction type
 */
case class rvcExpander(rvc_inst:Bits) extends Area{
	import TouchCore.rvc._
	val rv32_inst = Bits(32 bits)
	val imm = IMM(rvc_inst)
	private def rviEncoder = TouchCore.rv32i.rviEncoder
	switch(rvc_inst) {
		is(ADDI4SPN) {
			rv32_inst:=rviEncoder(
				c_rd = rvc_inst(CIW.rd).asUInt,
				c_rs1 = U(2),
				c_imm = imm.ADDI4SPN,
				i_func3 = B"3'b000",
				i_opcode = B"7'b0010011").I_encode
		}
		is(LW) {
			rv32_inst:=rviEncoder(
				c_rd = rvc_inst(CL.rd).asUInt,
				c_rs1 = rvc_inst(CL.rs1).asUInt,
				c_imm = imm.LW,
				i_func3 = B"3'b010",
				i_opcode = B"7'b0000011"
			).I_encode
		}
		is(SW) {
			rv32_inst := rviEncoder(
				c_rs1 = rvc_inst(CS.rs1).asUInt,
				c_rs2 = rvc_inst(CS.rs2).asUInt,
				c_imm = imm.SW,
				i_func3 = B"3'b101",
				i_opcode = B"7'b0100011"
			).S_encode
		}
		is(ADDI) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CI.rd).asUInt,
				c_rs1 = rvc_inst(CI.rs1).asUInt,
				c_imm = imm.ADDI,
				i_func3 = B"3'b000",
				i_opcode = B"7'b0100011"
			).I_encode
		}
		is(JAL) {
			rv32_inst := rviEncoder(
				c_rd = U(1),
				c_imm = imm.JAL,
				i_opcode = B"7'b1101111"
			).J_encode
		}
		is(LI) {
			when(rvc_inst(CI.rd) === B(0)) {
				rv32_inst := rviEncoder().ERR_encode
			} otherwise {
				rv32_inst := rviEncoder(
					c_rd = rvc_inst(CI.rd).asUInt,
					c_rs1 = U(0),
					c_imm = imm.LI,
					i_func3 = B"3'b000",
					i_opcode = B"7'b0100011"
				).I_encode
			}
		}
		is(ADDI16SP) {
			switch(rvc_inst(CI.rd)) {
				is(B(2)) {
					rv32_inst := rviEncoder(
						c_rd = U(2),
						c_rs1 = U(2),
						c_imm = imm.ADDI16SP,
						i_func3 = B"3'b000",
						i_opcode = B"7'b0100011"
					).I_encode
				}
				is(B(0)) {
					rv32_inst := rviEncoder().ERR_encode
				}
				default {
					rv32_inst := rviEncoder(
						c_rd = rvc_inst(CI.rd).asUInt,
						c_imm = imm.LUI,
						i_opcode = B"0110111"
					).U_encode
				}
			}
		}
		is(SRLI) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CB.rd).asUInt.resize(5),
				c_rs1 = rvc_inst(CB.rs1).asUInt.resize(5),
				c_imm = imm.SRLI,
				i_func3 = B"3'b101",
				i_opcode = B"7'b0010011"
			).I_encode
		}
		is(SRAI) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CB.rd).asUInt.resize(5),
				c_rs1 = rvc_inst(CB.rs1).asUInt.resize(5),
				c_imm = imm.SRAI,
				i_func3 = B"3'b101",
				i_opcode = B"7'b0010011"
			).I_encode
		}
		is(ANDI) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CB.rd).asUInt.resize(5),
				c_rs1 = rvc_inst(CB.rs1).asUInt.resize(5),
				c_imm = imm.ANDI,
				i_func3 = B"3'b111",
				i_opcode = B"7'b0010011"
			).I_encode
		}
		is(SUB) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CA.rd).asUInt.resize(5),
				c_rs1 = rvc_inst(CA.rs1).asUInt.resize(5),
				c_rs2 = rvc_inst(CA.rs2).asUInt.resize(5),
				i_func7 = B"7'b0100000",
				i_func3 = B"3'b000",
				i_opcode = B"7'b0110011"
			).I_encode
		}
		is(XOR) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CA.rd).asUInt.resize(5),
				c_rs1 = rvc_inst(CA.rs1).asUInt.resize(5),
				c_rs2 = rvc_inst(CA.rs2).asUInt.resize(5),
				i_func7 = B"7'b0000000",
				i_func3 = B"3'b100",
				i_opcode = B"7'b0110011"
			).I_encode
		}
		is(OR) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CA.rd).asUInt.resize(5),
				c_rs1 = rvc_inst(CA.rs1).asUInt.resize(5),
				c_rs2 = rvc_inst(CA.rs2).asUInt.resize(5),
				i_func7 = B"7'b0000000",
				i_func3 = B"3'b110",
				i_opcode = B"7'b0110011"
			).I_encode
		}
		is(AND) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CA.rd).asUInt.resize(5),
				c_rs1 = rvc_inst(CA.rs1).asUInt.resize(5),
				c_rs2 = rvc_inst(CA.rs2).asUInt.resize(5),
				i_func7 = B"7'b0000000",
				i_func3 = B"3'b111",
				i_opcode = B"7'b0110011"
			).I_encode
		}
		is(J) {
			rv32_inst := rviEncoder(
				c_rd = U(0),
				c_imm = imm.J,
				i_opcode = B"7'b1101111"
			).J_encode
		}
		is(BEQZ) {
			rv32_inst := rviEncoder(
				c_rs1 = rvc_inst(CB.rs1).asUInt,
				c_rs2 = U(0),
				c_imm = imm.BEQZ,
				i_func3 = B"3'b000",
				i_opcode = B"7'b1100011"
			).B_encode
		}
		is(BNEZ) {
			rv32_inst := rviEncoder(
				c_rs1 = rvc_inst(CB.rs1).asUInt,
				c_rs2 = U(0),
				c_imm = imm.BNEZ,
				i_func3 = B"3'b001",
				i_opcode = B"7'b1100011"
			).B_encode
		}
		is(SLLI) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CI.rd).asUInt,
				c_rs1 = rvc_inst(CB.rs1).asUInt,
				c_imm = imm.SLLI,
				i_func3 = B"3'b001",
				i_opcode = B"7'b0010011"
			).I_encode
		}
		is(LWSP) {
			rv32_inst := rviEncoder(
				c_rd = rvc_inst(CI.rd).asUInt,
				c_rs1 = U(2),
				c_imm = imm.LWSP,
				i_func3 = B"3'b010",
				i_opcode = B"7'b0000011"
			).I_encode
		}
		is(JR) {
			switch((rvc_inst(CR.rd) === B(0)) ## (rvc_inst(CR.rs2) === B(0))) {
				is(B"2'b10") {
					rv32_inst := rviEncoder(
						c_rd = U(0),
						c_rs1 = rvc_inst(CR.rs1).asUInt,
						c_imm = S(0),
						i_func3 = B"3'b000",
						i_opcode = B"7'b1100111"
					).I_encode
				}
				is(B"2'b11") {
					rv32_inst := rviEncoder(
						c_rd = rvc_inst(CR.rd).asUInt,
						c_rs1 = U(0),
						c_rs2 = rvc_inst(CR.rs2).asUInt,
						i_func3 = B"3'b000",
						i_func7 = B"7'b0000000",
						i_opcode = B"7'b0110011"
					).R_encode
				}
				default {
					rv32_inst := rviEncoder().ERR_encode
				}
			}
		}
		is(EBREAK) {
			switch((rvc_inst(CR.rd) === B(0)) ## (rvc_inst(CR.rs2) === B(0))) {
				is(B"2'b00") {
					rv32_inst := rviEncoder(
						c_rd = U(0),
						c_rs1 = U(0),
						c_imm = S(1),
						i_func3 = B"3'b000",
						i_opcode = B"7'b1110011"
					).I_encode
				}
				is(B"2'b10") {
					rv32_inst := rviEncoder(
						c_rd = U(1),
						c_rs1 = rvc_inst(CR.rs1).asUInt,
						c_imm = S(0),
						i_func3 = B"3'b000",
						i_opcode = B"7'b1100111"
					).I_encode
				}
				is(B"2'b11") {
					rv32_inst := rviEncoder(
						c_rd = rvc_inst(CR.rd).asUInt,
						c_rs1 = rvc_inst(CR.rs1).asUInt,
						c_rs2 = rvc_inst(CR.rs2).asUInt,
						i_func3 = B"3'b000",
						i_func7 = B"7'b0000000",
						i_opcode = B"7'b0110011"
					).R_encode
				}
				default {
					rv32_inst := rviEncoder().ERR_encode
				}
			}
		}
		is(SWSP) {
			rv32_inst := rviEncoder(
				c_rs1 = U(2),
				c_rs2 = rvc_inst(CSS.rs2).asUInt,
				c_imm = imm.SWSP,
				i_func3 = B"3'b101",
				i_opcode = B"7'b0100011"
			).R_encode
		}
		default {
			rv32_inst := rviEncoder().ERR_encode
		}
	}
}
