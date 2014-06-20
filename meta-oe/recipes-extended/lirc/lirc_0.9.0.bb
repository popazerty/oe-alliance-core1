require lirc.inc

PR = "${INCPR}.2"

SRC_URI[md5sum] = "b232aef26f23fe33ea8305d276637086"
SRC_URI[sha256sum] = "6323afae6ad498d4369675f77ec3dbb680fe661bea586aa296e67f2e2daba4ff"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3"

SRC_URI_append = " \
    file://lirc-0.9.0-neutrino-uinput-hack.diff;patch=1 \
    file://lirc-0.9.0-try_first_last_remote.diff;patch=1 \
    file://lirc-0.9.0-uinput-repeat-fix.diff;patch=1 \
    file://lirc-0.9.0-repeat_and_delay_hack.patch \
    file://lircd.init \
    file://lircmd.init \
    file://lircexec.init \
"

SRC_URI_append_spark = " \
    file://rename_input_device_to_be_spark.patch \
    file://lircd_spark.conf \
"

SRC_URI_append_spark7162 = " \
    file://rename_input_device_to_be_spark.patch \
    file://lircd_spark.conf \
"

do_install_append_spark() {
        install -m 0644 ${WORKDIR}/lircd_spark.conf ${D}${sysconfdir}/lircd.conf
}

do_install_append_spark7162() {
        install -m 0644 ${WORKDIR}/lircd_spark.conf ${D}${sysconfdir}/lircd.conf
}

