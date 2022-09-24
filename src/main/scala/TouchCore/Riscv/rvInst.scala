package TouchCore.Riscv

import spinal.core._

object rvInst extends Nameable {
	import const._
	abstract class rvInst{
		def apply:MaskedLiteral
	}

	case class I_type(key:MaskedLiteral) extends rvInst{
		def apply = key
		def encode(i_rd: UInt,
		           i_rs1: UInt,
		           i_imm: SInt) = rviEncoder().I_encode(
			i_rd = i_rd,
			i_rs1 = i_rs1,
			i_imm = i_imm,
			i_func3 = key.asBits()(i_func3),
			i_opcode = key.asBits()(i_opcode))
		def imm(rvi_inst:Bits) = rviIMM(rvi_inst).I_IMM
	}

	case class R_type(key:MaskedLiteral) extends rvInst{
		def apply = key
		def encode(i_rd: UInt,
		           i_rs1: UInt,
		           i_rs2: UInt)=rviEncoder().R_encode(
			i_rd = i_rd,
			i_rs1 = i_rs1,
			i_rs2 = i_rs2,
			i_func3 = key.asBits()(i_func3),
			i_func7 = key.asBits()(i_func7),
			i_opcode = key.asBits()(i_opcode)
		)
	}

	case class S_type(key: MaskedLiteral) extends rvInst {
		def apply = key

		def encode(i_rs1: UInt,
		           i_rs2: UInt,
		           i_imm: SInt) = rviEncoder().S_encode(
			i_rs1 = i_rs1,
			i_rs2 = i_rs2,
			i_imm = i_imm,
			i_func3 = key.asBits()(i_func3),
			i_opcode = key.asBits()(i_opcode)
		)
		def imm(rvi_inst:Bits) = rviIMM(rvi_inst).S_IMM
	}

	case class B_type(key: MaskedLiteral) extends rvInst {
		def apply = key
		def encode(i_rs1: UInt,
		           i_rs2: UInt,
		           i_imm: SInt) = rviEncoder().B_encode(
			i_rs1 = i_rs1,
			i_rs2 = i_rs2,
			i_imm = i_imm,
			i_func3 = key.asBits()(i_func3),
			i_opcode = key.asBits()(i_opcode)
		)
		def imm(rvi_inst:Bits) = rviIMM(rvi_inst).B_IMM
	}

	case class U_type(key: MaskedLiteral) extends rvInst {
		def apply = key

		def encode(i_rd: UInt,
		           i_imm: SInt) = rviEncoder().U_encode(
			i_rd = i_rd,
			i_imm = i_imm,
			i_opcode = key.asBits()(i_opcode)
		)
		def imm(rvi_inst:Bits) = rviIMM(rvi_inst).U_IMM
	}

	case class J_type(key: MaskedLiteral) extends rvInst {
		def apply = key

		def encode(i_rd: UInt,
		           i_imm: SInt) = rviEncoder().J_encode(
			i_rd = i_rd,
			i_imm = i_imm,
			i_opcode = key.asBits()(i_opcode)
		)
		def imm(rvi_inst:Bits) = rviIMM(rvi_inst).J_IMM
	}

	def I_Inst(key: MaskedLiteral) = I_type(key)
	def R_Inst(key: MaskedLiteral) = R_type(key)
	def S_Inst(key: MaskedLiteral) = S_type(key)
	def B_Inst(key: MaskedLiteral) = B_type(key)
	def U_Inst(key: MaskedLiteral) = U_type(key)
	def J_Inst(key: MaskedLiteral) = J_type(key)

	case class CR_type(key: MaskedLiteral) extends rvInst with CR_Range {
		def apply = key
		def rd_value(c_inst:Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst:Bits) = c_inst(rs1).asUInt
		def rs2_value(c_inst:Bits) = c_inst(rs2).asUInt
	}

	case class CI_type(key: MaskedLiteral) extends rvInst with CI_Range {
		def apply = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
	}

	case class CSS_type(key: MaskedLiteral) extends rvInst with CSS_Range {
		def apply = key
		def rs2_value(c_inst: Bits) = c_inst(rs2).asUInt
	}

	case class CIW_type(key: MaskedLiteral) extends rvInst with CIW_Range {
		def apply = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
	}

	case class CL_type(key: MaskedLiteral) extends rvInst with CL_Range {
		def apply = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
	}

	case class CS_type(key: MaskedLiteral) extends rvInst with CS_Range {
		def apply = key
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
		def rs2_value(c_inst: Bits) = c_inst(rs2).asUInt
	}

	case class CA_type(key: MaskedLiteral) extends rvInst with CA_Range {
		def apply = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
		def rs2_value(c_inst: Bits) = c_inst(rs2).asUInt
	}

	case class CB_type(key: MaskedLiteral) extends rvInst with CB_Range {
		def apply = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
	}

	case class CJ_type(key: MaskedLiteral) extends rvInst with CJ_Range {
		def apply = key
	}

	def CR_Inst(key: MaskedLiteral) = CR_type(key)
	def CI_Inst(key: MaskedLiteral) = CI_type(key)
	def CSS_Inst(key: MaskedLiteral) = CSS_type(key)
	def CIW_Inst(key: MaskedLiteral) = CIW_type(key)
	def CL_Inst(key: MaskedLiteral) = CL_type(key)
	def CS_Inst(key: MaskedLiteral) = CS_type(key)
	def CA_Inst(key: MaskedLiteral) = CA_type(key)
	def CB_Inst(key: MaskedLiteral) = CB_type(key)
	def CJ_Inst(key: MaskedLiteral) = CJ_type(key)

}
