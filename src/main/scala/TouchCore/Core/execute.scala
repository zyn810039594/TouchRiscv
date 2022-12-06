package TouchCore.Core

import TouchCore.ALU.{aluCal, aluShift,aluCalFunc,aluShiftFunc}
import TouchCore.BaseType.{TouchArea, TouchModule}
import spinal.core._
import spinal.lib._
import TouchCore.Riscv.rv32i._

class execute extends TouchModule {
	val io = new Bundle {
		val inst_ch = slave(Stream(Bits(iDataWidth bits)))
		val rs_in = Vec(in SInt(dDataWidth bits),2)
		val rd_out = master(Flow(UInt(dDataWidth bits)))
		val jp_ch = master(Stream(Bits(iAddrWidth bits)))
	}
	def rs1 = io.rs_in(0)
	def rs2 = io.rs_in(1)
	def rd=io.rd_out

	def inst = io.inst_ch.payload

	val cal_mod = new aluCal(dDataWidth)
	def cal_func = cal_mod.io.func
	def cal_en = cal_mod.io.enable
	def cal_numA = cal_mod.io.num_A
	def cal_numB = cal_mod.io.num_B
	def cal_res = cal_mod.io.num_out
	def cal_zero = cal_mod.io.zero

	cal_numA :=  rs1

	val shift_mod = new aluShift(dDataWidth)
	def shift_func = shift_mod.io.func
	def shift_en = shift_mod.io.enable
	def shift_numA = shift_mod.io.num_A
	def shift_numB = shift_mod.io.num_B
	def shift_res = shift_mod.io.num_out

	shift_numA := rs1

	when(io.inst_ch.valid) {
		switch(io.inst_ch.payload) {
			is(ADD()) {
				cal_func:=aluCalFunc.f_plus
				cal_en:=True
				cal_numB:=rs2
				rd.payload:=cal_res
			}
			is(SUB()) {
				cal_func := aluCalFunc.f_sub
				cal_en := True
				cal_numB := rs2
				rd.payload := cal_res
			}
			is(SLL()) {
				shift_func:=aluShiftFunc.f_sll
				shift_en := True
				shift_numB := rs2.asUInt.resized
				rd.payload:=shift_res
			}
			is(SLT()) {
				cal_func := aluCalFunc.f_comp
				cal_en := True
				cal_numB := rs2
				rd.payload := cal_res
			}
			is(SLTU()) {
				cal_func := aluCalFunc.f_compu
				cal_en := True
				cal_numB := rs2
				rd.payload := cal_res
			}
			is(XOR()) {
				cal_func := aluCalFunc.f_xor
				cal_en := True
				cal_numB := rs2
				rd.payload := cal_res
			}
			is(SRL()) {
				shift_func := aluShiftFunc.f_srl
				shift_en := True
				shift_numB := rs2.asUInt.resized
				rd.payload := shift_res
			}
			is(SRA()) {
				shift_func := aluShiftFunc.f_sra
				shift_en := True
				shift_numB := rs2.asUInt.resized
				rd.payload := shift_res
			}
			is(OR()) {
				cal_func := aluCalFunc.f_or
				cal_en := True
				cal_numB := rs2
				rd.payload := cal_res
			}
			is(AND()) {
				cal_func := aluCalFunc.f_and
				cal_en := True
				cal_numB := rs2
				rd.payload := cal_res
			}
			is(ADDI()) {
				cal_func := aluCalFunc.f_and
				cal_en := True
				cal_numB := ADDI.imm(inst)
				rd.payload := cal_res
			}
			is(SLLI()) {
				shift_func := aluShiftFunc.f_sll
				shift_en := True
				shift_numB := SLLI.imm(inst).asUInt.resized
				rd.payload := shift_res
			}
			is(SLTI()) {
				cal_func := aluCalFunc.f_comp
				cal_en := True
				cal_numB := SLTI.imm(inst)
				rd.payload := cal_res
			}
			is(SLTIU()) {
				cal_func := aluCalFunc.f_compu
				cal_en := True
				cal_numB := SLTI.imm(inst)
				rd.payload := cal_res
			}
			is(XORI()) {
				cal_func := aluCalFunc.f_xor
				cal_en := True
				cal_numB := XORI.imm(inst)
				rd.payload := cal_res
			}
			is(SRLI()) {
				shift_func := aluShiftFunc.f_srl
				shift_en := True
				shift_numB := SRLI.imm(inst).asUInt.resized
				rd.payload := shift_res
			}
			is(SRAI()) {
				shift_func := aluShiftFunc.f_sra
				shift_en := True
				shift_numB := SRAI.imm(inst).asUInt.resized
				rd.payload := shift_res
			}
			is(ORI()) {
				cal_func := aluCalFunc.f_or
				cal_en := True
				cal_numB := ORI.imm(inst)
				rd.payload := cal_res
			}
			is(ANDI()) {
				cal_func := aluCalFunc.f_and
				cal_en := True
				cal_numB := ANDI.imm(inst)
				rd.payload := cal_res
			}
			is(LB()) {

			}
			is(LH()) {

			}
			is(LW()) {

			}
			is(LBU()) {

			}
			is(LHU()) {

			}
			is(LWU()) {

			}
			is(SB()) {

			}
			is(SH()) {

			}
			is(SW()) {

			}
			is(BEQ()) {

			}
			is(BNE()) {

			}
			is(BLT()) {

			}
			is(BGE()) {

			}
			is(BLTU()) {

			}
			is(BGEU()) {

			}
			is(JALR()) {

			}
			is(JAL()) {

			}
			is(LUI()) {

			}
			is(AUIPC()) {

			}
			is(ECALL()) {

			}
			is(EBREAK()) {

			}
			is(FENCE()) {

			}
			is(CSRRW()) {

			}
			is(CSRRS()) {

			}
			is(CSRRC()) {

			}
			is(CSRRWI()) {

			}
			is(CSRRSI()) {

			}
			is(CSRRCI()) {

			}
		}
	}
}
