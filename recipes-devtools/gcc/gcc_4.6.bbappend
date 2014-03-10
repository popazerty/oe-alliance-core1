FILESEXTRAPATHS := "${THISDIR}/gcc-4.6"
PRINC := "${@int(PRINC) + 4}"

SRC_URI_sh4 += "file://gcc-4.6.3-stm-120618.patch"


