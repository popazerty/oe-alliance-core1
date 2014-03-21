require gcc-cross_${PV}.bb
require gcc-crosssdk.inc

SRC_URI_append_sh4 = "\
    file://gcc-4.6.3-stm-120618.patch \
    "
