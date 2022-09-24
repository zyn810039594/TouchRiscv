package TouchCore.Riscv

import spinal.core._

object const {
	def i_func7 = 31 downto 25

	def i_rd = 11 downto 7

	def i_func3 = 14 downto 12

	def i_rs1 = 19 downto 15

	def i_rs2 = 24 downto 20

	def i_opcode = 6 downto 0

	def c_op: Range.Inclusive = 1 downto 0

	def c_func2: Range.Inclusive = 6 downto 5

	def c_func3: Range.Inclusive = 15 downto 13

	def c_func4: Range.Inclusive = 15 downto 12

	trait CR_Range {
		def rd: Range.Inclusive = 11 downto 7

		def rs1: Range.Inclusive = rd

		def rs2: Range.Inclusive = 6 downto 2
	}

	trait CI_Range {

		def rd: Range.Inclusive = 11 downto 7

		def rs1: Range.Inclusive = rd
	}

	trait CSS_Range {
		def rs2: Range.Inclusive = 6 downto 2
	}

	trait CIW_Range {

		def rd: Range.Inclusive = 6 downto 2
	}

	trait CL_Range {
		def rs1: Range.Inclusive = 9 downto 7

		def rd: Range.Inclusive = 4 downto 2
	}

	trait CS_Range {
		def rs1: Range.Inclusive = 9 downto 7

		def rs2: Range.Inclusive = 4 downto 2
	}

	trait CA_Range {
		def rd: Range.Inclusive = 9 downto 7

		def rs1: Range.Inclusive = rd

		def rs2: Range.Inclusive = 4 downto 2
	}

	trait CB_Range {
		def rd: Range.Inclusive = 9 downto 7

		def rs1: Range.Inclusive = rd
	}

	trait CJ_Range

	object CR_REG extends CR_Range
	object CI_REG extends CI_Range
	object CSS_REG extends CSS_Range
	object CIW_REG extends CIW_Range
	object CL_REG extends CL_Range
	object CS_REG extends CS_Range
	object CA_REG extends CA_Range
	object CB_REG extends CB_Range
	object CJ_REG extends CJ_Range


	case class rvcIMM(rvc_inst: Bits) extends Area {
		def ADDI4SPN = S(12 bits,
			(5 downto 4) -> rvc_inst(12 downto 11),
			(9 downto 6) -> rvc_inst(10 downto 7),
			2 -> rvc_inst(7),
			3 -> rvc_inst(5),
			default -> false)

		def LW = S(12 bits,
			(5 downto 3) -> rvc_inst(12 downto 10),
			2 -> rvc_inst(6),
			6 -> rvc_inst(5),
			default -> false)

		def SW = LW

		def ADDI = S(6 bits,
			5 -> rvc_inst(12),
			(4 downto 0) -> rvc_inst(6 downto 2),
			default -> false).resize(12)

		def JAL = S(12 bits, 11 -> rvc_inst(12),
			4 -> rvc_inst(11),
			(9 downto 8) -> rvc_inst(10 downto 9),
			10 -> rvc_inst(8),
			6 -> rvc_inst(7),
			7 -> rvc_inst(6),
			(3 downto 1) -> rvc_inst(5 downto 3),
			5 -> rvc_inst(2),
			default -> false).resize(21)

		def LI = ADDI

		def ADDI16SP = S(10 bits,
			9 -> rvc_inst(12),
			4 -> rvc_inst(6),
			6 -> rvc_inst(5),
			(8 downto 7) -> rvc_inst(4 downto 3),
			5 -> rvc_inst(2),
			default -> false).resize(12)

		def LUI = S(18 bits,
			17 -> rvc_inst(12),
			(16 downto 12) -> rvc_inst(6 downto 2),
			default -> false).resize((21))

		def SRLI = S(12 bits,
			(11 downto 5) -> B"7'b00000000",
			(4 downto 0) -> rvc_inst(6 downto 2))

		def SRAI = S(12 bits,
			(11 downto 5) -> B"7'b01000000",
			(4 downto 0) -> rvc_inst(6 downto 2))

		def ANDI = S(6 bits,
			5 -> rvc_inst(12),
			(4 downto 0) -> rvc_inst(6 downto 2),
			default -> false).resize(12)

		def J = JAL

		def BEQZ = S(9 bits,
			8 -> rvc_inst(12),
			(4 downto 3) -> rvc_inst(11 downto 10),
			(7 downto 6) -> rvc_inst(6 downto 5),
			(2 downto 1) -> rvc_inst(4 downto 3),
			5 -> rvc_inst(2),
			default -> false).resize(13)

		def BNEZ = BEQZ

		def SLLI = SRLI

		def LWSP = S(8 bits,
			5 -> rvc_inst(12),
			(4 downto 2) -> rvc_inst(6 downto 4),
			(7 downto 6) -> rvc_inst(3 downto 2),
			default -> false)

		def SWSP = S(8 bits,
			(5 downto 2) -> rvc_inst(12 downto 9),
			(7 downto 6) -> rvc_inst(8 downto 7),
			default -> false).resize(12)
	}

	case class rviIMM(rvi_inst: Bits) extends Area {
		// immediates
		def I_IMM = S(rvi_inst(31 downto 20))

		def S_IMM = S(12 bits,
			(11 downto 5) -> rvi_inst(31 downto 25),
			(4 downto 0) -> rvi_inst(11 downto 7))

		def B_IMM = S(13 bits,
			12 -> rvi_inst(31),
			(10 downto 5) -> rvi_inst(30 downto 25),
			(4 downto 1) -> rvi_inst(11 downto 8),
			11 -> rvi_inst(7),
			default -> false)

		def U_IMM = S(21 bits,
			(31 downto 12) -> rvi_inst(31 downto 12))

		def J_IMM = S(21 bits,
			20 -> rvi_inst(31),
			(10 downto 1) -> rvi_inst(30 downto 21),
			11 -> rvi_inst(20),
			(19 downto 12) -> rvi_inst(19 downto 12),
			default -> false)
	}

	case class rviEncoder() extends Area {
		def R_encode(i_rd: UInt,
		             i_rs1: UInt,
		             i_rs2: UInt,
		             i_func3: Bits,
		             i_func7: Bits,
		             i_opcode: Bits) = B(32 bits,
			i_func7 -> i_func7,
			i_rs2 -> i_rs2.asBits,
			i_rs1 -> i_rs1.asBits,
			i_func3 -> i_func3,
			i_rd -> i_rd.asBits,
			i_opcode -> i_opcode)

		def I_encode(i_rd: UInt,
		             i_rs1: UInt,
		             i_imm: SInt,
		             i_func3: Bits,
		             i_opcode: Bits) = B(32 bits,
			(31 downto 20) -> i_imm(11 downto 0).asBits,
			i_rs1 -> i_rs1.asBits,
			i_func3 -> i_func3,
			i_rd -> i_rd.asBits,
			i_opcode -> i_opcode)

		def S_encode(i_rs1: UInt,
		             i_rs2: UInt,
		             i_imm: SInt,
		             i_func3: Bits,
		             i_opcode: Bits) = B(32 bits,
			(31 downto 25) -> i_imm(11 downto 5).asBits,
			i_rs2 -> i_rs2.asBits,
			i_rs1 -> i_rs1.asBits,
			i_func3 -> i_func3,
			(11 downto 7) -> i_imm(4 downto 0).asBits,
			i_opcode -> i_opcode)

		def B_encode(i_rs1: UInt,
		             i_rs2: UInt,
		             i_imm: SInt,
		             i_func3: Bits,
		             i_opcode: Bits) = B(32 bits,
			31 -> i_imm(12),
			(30 downto 25) -> i_imm(10 downto 5).asBits,
			i_rs2 -> i_rs2.asBits,
			i_rs1 -> i_rs1.asBits,
			i_func3 -> i_func3,
			(11 downto 8) -> i_imm(4 downto 1).asBits,
			7 -> i_imm(11),
			i_opcode -> i_opcode
		)

		def U_encode(i_rd: UInt,
		             i_imm: SInt,
		             i_opcode: Bits) = B(32 bits,
			(31 downto 12) -> i_imm(31 downto 12).asBits,
			i_rd -> i_rd.asBits,
			i_opcode -> i_opcode)

		def J_encode(i_rd: UInt,
		             i_imm: SInt,
		             i_opcode: Bits) = B(32 bits,
			31 -> i_imm(20),
			(30 downto 21) -> i_imm(10 downto 1).asBits,
			20 -> i_imm(11),
			(19 downto 12) -> i_imm(19 downto 12).asBits,
			i_rd -> i_rd.asBits,
			i_opcode -> i_opcode)
	}
}
