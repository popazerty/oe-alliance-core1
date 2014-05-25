FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append_sh4 = " \
    file://gcc-4.8.2-stm-131204.patch \
    file://SH4-Fix-PR58475-insn-swapb-does-not-satisfy-its-constraints.patch \
"
