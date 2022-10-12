package TouchCore.Riscv

import spinal.core._

object rv32i {
	import rvInst._
//	def opcode_J = M"1101111"
//	def opcode_I = M"00-0011",M"1100111",M"1110011",M"0001111"
	//R-type calculate instructions
	def ADD     = R_Inst(M"0000000----------000-----0110011")
	def SUB     = R_Inst(M"0100000----------000-----0110011")
	def SLL     = R_Inst(M"0000000----------001-----0110011")
	def SLT     = R_Inst(M"0000000----------010-----0110011")
	def SLTU    = R_Inst(M"0000000----------011-----0110011")
	def XOR     = R_Inst(M"0000000----------100-----0110011")
	def SRL     = R_Inst(M"0000000----------101-----0110011")
	def SRA     = R_Inst(M"0100000----------101-----0110011")
	def OR      = R_Inst(M"0000000----------110-----0110011")
	def AND     = R_Inst(M"0000000----------111-----0110011")

	//I-type calculate instructions
	def ADDI    = I_Inst(M"-----------------000-----0010011")
	def SLLI    = I_Inst(M"000000-----------001-----0010011")
	def SLTI    = I_Inst(M"-----------------010-----0010011")
	def SLTIU   = I_Inst(M"-----------------011-----0010011")
	def XORI    = I_Inst(M"-----------------100-----0010011")
	def SRLI    = I_Inst(M"000000-----------101-----0010011")
	def SRAI    = I_Inst(M"010000-----------101-----0010011")
	def ORI     = I_Inst(M"-----------------110-----0010011")
	def ANDI    = I_Inst(M"-----------------111-----0010011")

	//I-type load instructions
	def LB      = I_Inst(M"-----------------000-----0000011")
	def LH      = I_Inst(M"-----------------001-----0000011")
	def LW      = I_Inst(M"-----------------010-----0000011")
	def LBU     = I_Inst(M"-----------------100-----0000011")
	def LHU     = I_Inst(M"-----------------101-----0000011")
	def LWU     = I_Inst(M"-----------------110-----0000011")

	//S-type store instructions
	def SB      = S_Inst(M"-----------------000-----0100011")
	def SH      = S_Inst(M"-----------------001-----0100011")
	def SW      = S_Inst(M"-----------------010-----0100011")

	//B-type branch instructions
	def BEQ     = B_Inst(M"-----------------000-----1100011")
	def BNE     = B_Inst(M"-----------------001-----1100011")
	def BLT     = B_Inst(M"-----------------100-----1100011")
	def BGE     = B_Inst(M"-----------------101-----1100011")
	def BLTU    = B_Inst(M"-----------------110-----1100011")
	def BGEU    = B_Inst(M"-----------------111-----1100011")

	//I-type jump instructions
	def JALR    = I_Inst(M"-----------------000-----1100111")

	//J-type jump instructions
	def JAL     = J_Inst(M"----------0--------------1101111")

	//U-type instructions
	def LUI     = U_Inst(M"-------------------------0110111")
	def AUIPC   = U_Inst(M"-------------------------0010111")

	//Exceptions
	def ECALL   = I_Inst(M"00000000000000000000000001110011")
	def EBREAK  = I_Inst(M"00000000000100000000000001110011")

	//See as nop
	def FENCE   = I_Inst(M"-------------------------0001111")

	//I-type CSR instructions
	def CSRRW   = I_Inst(M"-----------------001-----1110011")
	def CSRRS   = I_Inst(M"-----------------010-----1110011")
	def CSRRC   = I_Inst(M"-----------------011-----1110011")
	def CSRRWI  = I_Inst(M"-----------------101-----1110011")
	def CSRRSI  = I_Inst(M"-----------------110-----1110011")
	def CSRRCI  = I_Inst(M"-----------------111-----1110011")
}
