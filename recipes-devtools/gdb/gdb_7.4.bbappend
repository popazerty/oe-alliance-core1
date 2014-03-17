LTTNGUST_sh4 = ""

FILESEXTRAPATHS := "${THISDIR}/gdb-7.4"
SRC_URI_append_sh4 = "\
	file://renesas-sh-native-support.patch;patch=1 \
"
