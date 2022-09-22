package TouchCore

import spinal.core._

object rv32i {
	def func7 = 31 downto 25

	def rd = 11 downto 7

	def func3 = 14 downto 12

	def rs1 = 19 downto 15

	def rs2 = 24 downto 20

	def opcode = 6 downto 0
	case class IMM(rvi_inst: Bits) extends Area {
		// immediates
		def I_type = S(rvi_inst(31 downto 20))
		def S_type = S(12 bits,
			(11 downto 5)->rvi_inst(31 downto 25),
			(4 downto 0)->rvi_inst(11 downto 7))
		def B_type = S(13 bits,
			12->rvi_inst(31),
			(10 downto 5)->rvi_inst(30 downto 25),
			(4 downto 1)->rvi_inst(11 downto 8),
			11->rvi_inst(7),
			default->false)
		def U_type = S(21 bits,
			(31 downto 12)->rvi_inst(31 downto 12))
		def J_type = S(21 bits,
			20->rvi_inst(31),
			(10 downto 1)-> rvi_inst(30 downto 21),
			11->rvi_inst(20),
			(19 downto 12)-> rvi_inst(19 downto 12),
			default->false)
	}
	case class rviEncoder(c_rd:UInt=null,
	                      c_rs1:UInt=null,
	                      c_rs2:UInt=null,
	                      c_imm:SInt=null,
	                      i_func3:Bits=null,
	                      i_func7:Bits=null,
	                      i_opcode:Bits=null) extends Area{
		def R_encode = B(32 bits,
			func7->i_func7,
			rs2->c_rs2.asBits,
			rs1->c_rs1.asBits,
			func3->i_func3,
			rd->c_rd.asBits,
			opcode->i_opcode)
		def I_encode = B(32 bits,
			(31 downto 20)->c_imm(11 downto 0).asBits,
			rs1->c_rs1.asBits,
			func3->i_func3,
			rd->c_rd.asBits,
			opcode->i_opcode)
		def S_encode = B(32 bits,
			(31 downto 25)->c_imm(11 downto 5).asBits,
			rs2->c_rs2.asBits,
			rs1->c_rs1.asBits,
			func3->i_func3,
			(11 downto 7)->c_imm(4 downto 0).asBits,
			opcode->i_opcode)
		def B_encode = B(32 bits,
			31->c_imm(12),
			(30 downto 25)->c_imm(10 downto 5).asBits,
			rs2->c_rs2.asBits,
			rs1->c_rs1.asBits,
			func3->i_func3,
			(11 downto 8)->c_imm(4 downto 1).asBits,
			7->c_imm(11),
			opcode->i_opcode
		)
		def U_encode = B(32 bits,
			(31 downto 12)->c_imm(31 downto 12).asBits,
			rd->c_rd.asBits,
			opcode->i_opcode)
		def J_encode = B(32 bits,
			31->c_imm(20),
			(30 downto 21)->c_imm(10 downto 1).asBits,
			20->c_imm(11),
			(19 downto 12)->c_imm(19 downto 12).asBits,
			rd->c_rd.asBits,
			opcode->i_opcode)
		def ERR_encode = B(32 bits,default->true)
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
