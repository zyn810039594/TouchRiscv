package TouchCore

import spinal.core._


object rvc {
	def op: Range.Inclusive = 1 downto 0

	def func2: Range.Inclusive = 6 downto 5

	def func3: Range.Inclusive = 15 downto 13

	def func4: Range.Inclusive = 15 downto 12

	case class IMM(rvc_inst: Bits) extends Area {
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

	object CR {
		def rd: Range.Inclusive = 11 downto 7

		def rs1: Range.Inclusive = rd

		def rs2: Range.Inclusive = 6 downto 2
	}

	object CI {
		def imm_0: Range.Inclusive = 12 downto 12

		def rd: Range.Inclusive = 11 downto 7

		def rs1: Range.Inclusive = rd

		def imm_1: Range.Inclusive = 6 downto 2
	}

	object CSS {
		def imm_0: Range.Inclusive = 12 downto 7

		def rs2: Range.Inclusive = 6 downto 2
	}

	object CIW {
		def imm_0: Range.Inclusive = 12 downto 5

		def rd: Range.Inclusive = 6 downto 2
	}

	object CL {
		def imm_0: Range.Inclusive = 12 downto 10

		def rs1: Range.Inclusive = 9 downto 7

		def imm_1: Range.Inclusive = 6 downto 5

		def rd: Range.Inclusive = 4 downto 2
	}

	object CS {
		def imm_0: Range.Inclusive = 12 downto 10

		def rs1: Range.Inclusive = 9 downto 7

		def imm_1: Range.Inclusive = 6 downto 5

		def rs2: Range.Inclusive = 4 downto 2
	}

	object CA {
		def rd: Range.Inclusive = 9 downto 7

		def rs1: Range.Inclusive = rd

		def rs2: Range.Inclusive = 4 downto 2
	}

	object CB {
		def offset_0: Range.Inclusive = 12 downto 12

		def rd: Range.Inclusive = 9 downto 7

		def rs1: Range.Inclusive = rd

		def offset_1: Range.Inclusive = 6 downto 2
	}

	object CJ {
		def jump_target: Range.Inclusive = 12 downto 2
	}

	def ADDI4SPN = M"000-----------00"

	def LW = M"010-----------00"

	def SW = M"110-----------00"

	// NOP and ADDI
	def ADDI = M"000-----------01"

	def JAL = M"001-----------01"

	def LI = M"010-----------01"

	// ADDI16SP and LUI
	def ADDI16SP = M"011-----------01"

	def SRLI = M"100-00--------01"

	def SRAI = M"100-01--------01"

	def ANDI = M"100-10--------01"

	def SUB = M"100-11---00---01"

	def XOR = M"100-11---01---01"

	def OR = M"100-11---10---01"

	def AND = M"100-11---11---01"

	def J = M"101-----------01"

	def BEQZ = M"110-----------01"

	def BNEZ = M"111-----------01"

	def SLLI = M"000-----------10"

	def LWSP = M"010-----------10"

	// JR and MV
	def JR = M"1000----------10"

	// EBREAK, JALR and ADD
	def EBREAK = M"1001----------10"

	def SWSP = M"110-----------10"
}
