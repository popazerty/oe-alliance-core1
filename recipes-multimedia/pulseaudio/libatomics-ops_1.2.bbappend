PRINC = "1"

FILESEXTRAPATHS_append := "${THISDIR}/${PN}"

SRC_URI_append_sh4 = " \
           file://gentoo/sh4-atomic-ops.patch \
"

