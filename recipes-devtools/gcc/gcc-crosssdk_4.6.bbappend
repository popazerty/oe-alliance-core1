FILESEXTRAPATHS := "${THISDIR}/gcc-4.6"
PRINC := "${@int(PRINC) + 5}"

SRC_URI_append_sh4 = "\
	file://gcc-4.6.3-stm-120618.patch \
	"

