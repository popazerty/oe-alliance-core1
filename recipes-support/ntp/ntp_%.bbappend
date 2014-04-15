PRINC = "4"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_sh4 = " \
    file://ntp-fix-sh4-compile-gcc48.patch;patch=1 \
    "
