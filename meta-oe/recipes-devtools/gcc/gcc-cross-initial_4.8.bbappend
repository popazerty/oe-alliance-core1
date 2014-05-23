FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " \
    file://SH4-Fix-PR58475-insn-swapb-does-not-satisfy-its-constraints.patch \
"
