package TouchCore.BaseType

trait TouchConfig {
	def threeStage: Boolean = false
	def FPGAMode: Boolean = true
	def rviEnabled: Boolean = false
	def rvcEnabled: Boolean = true
	def csrEnabled: Boolean = true
	def triModeEnabled: Boolean = false

	def iAddrWidth: Int = 32
	def iDataWidth: Int = 32

	def dAddrWidth: Int = 32

	def dDataWidth: Int = 32
}
