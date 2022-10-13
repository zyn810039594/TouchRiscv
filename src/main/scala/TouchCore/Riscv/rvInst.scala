package TouchCore.Riscv

import spinal.core._

object rvInst extends Nameable {
	import const._
	object instType extends SpinalEnum {
		val i_type, r_type, s_type, b_type, u_type, j_type, c_type = newElement()
	}
	object instPreDecodeType extends SpinalEnum{
		val J_Type,IC_Type,IL_Type,IJ_Type,S_Type,B_Type,UL_Type,UA_Type,CSR_Type,FENCE_Type = newElement()
	}

	abstract class rvInst{
		def apply:MaskedLiteral
		def opcode:Bits = apply.asBits(false)(6 downto 0)
		def iType:instType.C
	}

	trait regUse{
		def rd_used:Boolean
		def rs1_used:Boolean
		def rs2_used:Boolean
	}

	case class I_Type(key:MaskedLiteral) extends rvInst with regUse {
		def apply() = key
		def rd_used: Boolean = true
		def rs1_used: Boolean = true
		def rs2_used: Boolean = false
		def iType = instType.i_type
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

	case class R_Type(key:MaskedLiteral) extends rvInst with regUse {
		def apply() = key
		def rd_used: Boolean = true
		def rs1_used: Boolean = true
		def rs2_used: Boolean = true
		def iType = instType.r_type
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

	case class S_Type(key: MaskedLiteral) extends rvInst with regUse{
		def apply() = key
		def rd_used: Boolean = false
		def rs1_used: Boolean = true
		def rs2_used: Boolean = true
		def iType = instType.s_type
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

	case class B_Type(key: MaskedLiteral) extends rvInst with regUse{
		def apply() = key
		def rd_used: Boolean = false
		def rs1_used: Boolean = true
		def rs2_used: Boolean = true
		def iType = instType.b_type
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

	case class U_Type(key: MaskedLiteral) extends rvInst with regUse{
		def apply() = key

		def rd_used: Boolean = true
		def rs1_used: Boolean = false
		def rs2_used: Boolean = false
		def iType = instType.u_type

		def encode(i_rd: UInt,
		           i_imm: SInt) = rviEncoder().U_encode(
			i_rd = i_rd,
			i_imm = i_imm,
			i_opcode = key.asBits()(i_opcode)
		)
		def imm(rvi_inst:Bits) = rviIMM(rvi_inst).U_IMM
	}

	case class J_Type(key: MaskedLiteral) extends rvInst with regUse{
		def apply() = key
		def rd_used: Boolean = true
		def rs1_used: Boolean = false
		def rs2_used: Boolean = false
		def iType = instType.j_type
		def encode(i_rd: UInt,
		           i_imm: SInt) = rviEncoder().J_encode(
			i_rd = i_rd,
			i_imm = i_imm,
			i_opcode = key.asBits()(i_opcode)
		)
		def imm(rvi_inst:Bits) = rviIMM(rvi_inst).J_IMM
	}

	def I_Inst(key: MaskedLiteral) = I_Type(key)
	def R_Inst(key: MaskedLiteral) = R_Type(key)
	def S_Inst(key: MaskedLiteral) = S_Type(key)
	def B_Inst(key: MaskedLiteral) = B_Type(key)
	def U_Inst(key: MaskedLiteral) = U_Type(key)
	def J_Inst(key: MaskedLiteral) = J_Type(key)

	case class CR_Type(key: MaskedLiteral) extends rvInst with CR_Range {
		def apply() = key
		def rd_value(c_inst:Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst:Bits) = c_inst(rs1).asUInt
		def rs2_value(c_inst:Bits) = c_inst(rs2).asUInt
		def iType = instType.c_type
	}

	case class CI_Type(key: MaskedLiteral) extends rvInst with CI_Range {
		def apply() = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
		def iType = instType.c_type
	}

	case class CSS_Type(key: MaskedLiteral) extends rvInst with CSS_Range {
		def apply() = key
		def rs2_value(c_inst: Bits) = c_inst(rs2).asUInt
		def iType = instType.c_type
	}

	case class CIW_Type(key: MaskedLiteral) extends rvInst with CIW_Range {
		def apply() = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def iType = instType.c_type
	}

	case class CL_Type(key: MaskedLiteral) extends rvInst with CL_Range {
		def apply() = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
		def iType = instType.c_type
	}

	case class CS_Type(key: MaskedLiteral) extends rvInst with CS_Range {
		def apply() = key
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
		def rs2_value(c_inst: Bits) = c_inst(rs2).asUInt
		def iType = instType.c_type
	}

	case class CA_Type(key: MaskedLiteral) extends rvInst with CA_Range {
		def apply() = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
		def rs2_value(c_inst: Bits) = c_inst(rs2).asUInt
		def iType = instType.c_type
	}

	case class CB_Type(key: MaskedLiteral) extends rvInst with CB_Range {
		def apply() = key
		def rd_value(c_inst: Bits) = c_inst(rd).asUInt
		def rs1_value(c_inst: Bits) = c_inst(rs1).asUInt
		def iType = instType.c_type
	}

	case class CJ_Type(key: MaskedLiteral) extends rvInst with CJ_Range {
		def apply() = key
		def iType = instType.c_type
	}

	def CR_Inst(key: MaskedLiteral) = CR_Type(key)
	def CI_Inst(key: MaskedLiteral) = CI_Type(key)
	def CSS_Inst(key: MaskedLiteral) = CSS_Type(key)
	def CIW_Inst(key: MaskedLiteral) = CIW_Type(key)
	def CL_Inst(key: MaskedLiteral) = CL_Type(key)
	def CS_Inst(key: MaskedLiteral) = CS_Type(key)
	def CA_Inst(key: MaskedLiteral) = CA_Type(key)
	def CB_Inst(key: MaskedLiteral) = CB_Type(key)
	def CJ_Inst(key: MaskedLiteral) = CJ_Type(key)

}
