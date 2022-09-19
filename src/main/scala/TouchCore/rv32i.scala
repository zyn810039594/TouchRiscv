package TouchCore

import spinal.core._

object rv32i {
	case class IMM(instruction: Bits) extends Area {
		// immediates
		def i:Bits  =   instruction(31 downto 20)
		def s:Bits  =   instruction(31 downto 25) ## instruction(11 downto 7)
		def b:Bits  =   instruction(31) ## instruction(7) ## instruction(30 downto 25) ## instruction(11 downto 8)
		def u:Bits  =   instruction(31 downto 12) ## U"x000"
		def j:Bits  =   instruction(31) ## instruction(19 downto 12) ## instruction(20) ## instruction(30 downto 21)

		// sign-extend immediates
		def i_sext:Bits =   B((19 downto 0) -> i(11)) ## i
		def s_sext:Bits =   B((19 downto 0) -> s(11)) ## s
		def b_sext:Bits =   B((18 downto 0) -> b(11)) ## b ## False
		def j_sext:Bits =   B((10 downto 0) -> j(19)) ## j ## False
	}

	// I-type calculate instructions
	def ADD     =   M"0000000----------000-----0110011"
	def SUB     =   M"0100000----------000-----0110011"
	def SLL     =   M"0000000----------001-----0110011"
	def SLT     =   M"0000000----------010-----0110011"
	def SLTU    =   M"0000000----------011-----0110011"
	def XOR     =   M"0000000----------100-----0110011"
	def SRL     =   M"0000000----------101-----0110011"
	def SRA     =   M"0100000----------101-----0110011"
	def OR      =   M"0000000----------110-----0110011"
	def AND     =   M"0000000----------111-----0110011"
	//R-type calculate instructions
	def ADDI    =   M"-----------------000-----0010011"
	def SLLI    =   M"000000-----------001-----0010011"
	def SLTI    =   M"-----------------010-----0010011"
	def SLTIU   =   M"-----------------011-----0010011"
	def XORI    =   M"-----------------100-----0010011"
	def SRLI    =   M"000000-----------101-----0010011"
	def SRAI    =   M"010000-----------101-----0010011"
	def ORI     =   M"-----------------110-----0010011"
	def ANDI    =   M"-----------------111-----0010011"
	//I-type load instructions
	def LB      =   M"-----------------000-----0000011"
	def LH      =   M"-----------------001-----0000011"
	def LW      =   M"-----------------010-----0000011"
	def LBU     =   M"-----------------100-----0000011"
	def LHU     =   M"-----------------101-----0000011"
	def LWU     =   M"-----------------110-----0000011"
	//S-type store instructions
	def SB      =   M"-----------------000-----0100011"
	def SH      =   M"-----------------001-----0100011"
	def SW      =   M"-----------------010-----0100011"
	//B-type branch instructions
	def BEQ     =   M"-----------------000---0-1100011"
	def BNE     =   M"-----------------001---0-1100011"
	def BLT     =   M"-----------------100---0-1100011"
	def BGE     =   M"-----------------101---0-1100011"
	def BLTU    =   M"-----------------110---0-1100011"
	def BGEU    =   M"-----------------111---0-1100011"
	//I-type jump instructions
	def JALR    =   M"-----------------000-----1100111"
	//J-type jump instructions
	def JAL     =   M"----------0--------------1101111"
	//U-type instructions
	def LUI     =   M"-------------------------0110111"
	def AUIPC   =   M"-------------------------0010111"
	//Exceptions
	def ECALL   =   M"00000000000000000000000001110011"
	def EBREAK  =   M"00000000000100000000000001110011"
	//See as nop
	def FENCE   =   M"-------------------------0001111"
	//I-type CSR instructions
	def CSRRW   =   M"-----------------001-----1110011"
	def CSRRS   =   M"-----------------010-----1110011"
	def CSRRC   =   M"-----------------011-----1110011"
	def CSRRWI  =   M"-----------------101-----1110011"
	def CSRRSI  =   M"-----------------110-----1110011"
	def CSRRCI  =   M"-----------------111-----1110011"
}
