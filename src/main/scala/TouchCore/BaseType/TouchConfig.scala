package TouchCore.BaseType

trait TouchConfig {
	def threeStage: Boolean = false
	def FPGAMode: Boolean = true
	def rvcEnabled: Boolean = true
	def csrEnabled: Boolean = true
	def triModeEnabled: Boolean = false

	def iAddrWidth: Int = 32
	def iDataWidth: Int = 32
}
