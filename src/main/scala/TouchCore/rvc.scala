package TouchCore

import spinal.core._


object rvcInst extends SpinalEnum{
	val ADDI4SPN = newElement("CIW")
	val LW = newElement("CL")
	val SW = newElement("CS")
	val NOP,ADDI,LI,ADDI16SP,LUI,SLLI,LWSP = newElement("CI")
	val SUB,XOR,OR,AND = newElement("CA")
	val SRLI,SRAI,ANDI,BEQZ,BNEZ = newElement("CB")
	val JAL,J = newElement("CJ")
	val JR,MV,EBREAK,JALR,ADD = newElement("CR")
	val SWSP = newElement("CSS")
	val ERR = newElement("ERR")
	def op:Range.Inclusive = 1 downto 0
	def func2: Range.Inclusive = 6 downto 5
	def func3: Range.Inclusive = 15 downto 13
	def func4: Range.Inclusive = 12 downto 12
	def func5: Range.Inclusive = 11 downto 10
	object CR{
		def rd:Range.Inclusive = 11 downto 7
		def rs1: Range.Inclusive = rd
		def rs2:Range.Inclusive = 6 downto 2
	}
	object CI{
		def imm_0:Range.Inclusive = 12 downto 12
		def rd: Range.Inclusive = 11 downto 7
		def rs1: Range.Inclusive = rd
		def imm_1:Range.Inclusive = 6 downto 2
	}
	object CSS{
		def imm_0:Range.Inclusive = 12 downto 7
		def rs2:Range.Inclusive = 6 downto 2
	}
	object CIW{
		def imm_0:Range.Inclusive = 12 downto 5
		def rd:Range.Inclusive = 6 downto 2
	}
	object CL{
		def imm_0:Range.Inclusive = 12 downto 10
		def rs1:Range.Inclusive = 9 downto 7
		def imm_1:Range.Inclusive = 6 downto 5
		def rd:Range.Inclusive = 4 downto 2
	}
	object CS{
		def imm_0: Range.Inclusive = 12 downto 10
		def rs1: Range.Inclusive = 9 downto 7
		def imm_1: Range.Inclusive = 6 downto 5
		def rs2: Range.Inclusive = 4 downto 2
	}
	object CA{
		def rd:Range.Inclusive = 9 downto 7
		def rs1:Range.Inclusive = rd
		def rs2:Range.Inclusive = 4 downto 2
	}
	object CB{
		def offset_0:Range.Inclusive = 12 downto 10
		def rd:Range.Inclusive = 9 downto 7
		def rs1: Range.Inclusive = rd
		def offset_1:Range.Inclusive = 6 downto 2
	}
	object CJ{
		def jump_target:Range.Inclusive = 12 downto 2
	}
}

object rvc {
	import rvcInst._
	def rvcScan(cmd:Bits):Bool = cmd(1 downto 0)===B"2'b11"
	def rvcDecode(cmd:Bits){
		val result = rvcInst()
		switch(cmd(op)){
			is(B"2'b00"){
				switch(cmd(func3)){
					is(B"3'b000"){
						result := ADDI4SPN
					}
					is(B"3'b010"){
						result := LW
					}
					is(B"3'b110"){
						result := SW
					}
					default{
						result := ERR
					}
				}
			}
			is(B"2'b01"){
				switch(cmd(func3)){
					is(B"000"){
						when(cmd(CI.rd)===B(0)){
							result := NOP
						}otherwise{
							result := ADDI
						}
					}
					is(B"001"){
						result := JAL
					}
					is(B"010"){
						result := LI
					}
					is(B"011"){
						switch(cmd(CI.rd)){
							is(B(0)){
								result := ERR
							}
							is(B(2)){
								result := ADDI16SP
							}
							default{
								result := LUI
							}
						}
					}
					is(B"100"){
						switch(cmd())
					}
				}
			}
		}
		return result
	}

}
