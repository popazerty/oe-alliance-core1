PACKAGE_ARCH = "${MACHINEBUILD}"
PR = "r2"

do_compile_append_mipsel() {
    echo "arch ${TARGET_ARCH} $priority" >> $archconf
}

# add support for extra feeds
PACKAGE_ARCHS += " ocram"

do_compile_append_odinm9() {
    echo "arch et9x00 $priority" >> $archconf
}

do_compile_append_cube() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}

do_compile_append_dm800() {
    echo "arch mips32el $priority" >> $archconf
}

do_compile_append_sh4() {
    echo "arch mips32el $priority" >> $archconf
    echo "arch mipsel $priority" >> $archconf
}
