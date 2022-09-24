package TouchCore.Riscv

import spinal.core._

object rvc {
	import rvInst._

	def ADDI4SPN = CIW_Inst(M"000-----------00")

	def LW = CL_Inst(M"010-----------00")

	def SW = CS_Inst(M"110-----------00")

	// NOP and ADDI
	def ADDI = CI_Inst(M"000-----------01")

	def JAL = CJ_Inst(M"001-----------01")

	def LI = CI_Inst(M"010-----------01")

	// ADDI16SP and LUI
	def ADDI16SP = CI_Inst(M"011-----------01")

	def SRLI = CB_Inst(M"100-00--------01")

	def SRAI = CB_Inst(M"100-01--------01")

	def ANDI = CB_Inst(M"100-10--------01")

	def SUB = CA_Inst(M"100-11---00---01")

	def XOR = CA_Inst(M"100-11---01---01")

	def OR = CA_Inst(M"100-11---10---01")

	def AND = CA_Inst(M"100-11---11---01")

	def J = CJ_Inst(M"101-----------01")

	def BEQZ = CB_Inst(M"110-----------01")

	def BNEZ = CB_Inst(M"111-----------01")

	def SLLI = CI_Inst(M"000-----------10")

	def LWSP = CI_Inst(M"010-----------10")

	// JR and MV
	def JR = CR_Inst(M"1000----------10")

	// EBREAK, JALR and ADD
	def EBREAK = CR_Inst(M"1001----------10")

	def SWSP = CSS_Inst(M"110-----------10")
}
