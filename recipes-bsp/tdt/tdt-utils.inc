HOMEPAGE = "https://www.gitorious.org/open-duckbox-project-sh4/tdt"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = "tdt-dvb-modules-${MACHINE} \
    jpeg \
    stslave-fw \
    "

SRCREV = "${AUTOREV}"
INC_PR = "r1"
PV = "0.1+git${SRCPV}"

SRC_URI = " \
    git://github.com/sklnet/tdt-tools.git;protocol=git \
    file://evremote2.sh \
    "

S = "${WORKDIR}/git"

inherit autotools 

EXTRA_OECONF = "--enable-silent-rules --enable-maintainer-mode --prefix="

LDFLAGS += "-lpthread -lrt"

do_configure() {
    ./autogen.sh
    oe_runconf
}

do_compile() {
    oe_runmake  'DESTDIR=${D}' 
}

do_install() {
    oe_runmake  'DESTDIR=${D}' install
    install -d ${D}${includedir}/mmeimage
    install -m 644 ${WORKDIR}/git/libmmeimage/*.h ${D}${includedir}/mmeimage
    install -d ${D}/${sysconfdir}/init.d
    install -d ${D}/${sysconfdir}/rcS.d
    install -m 0755 ${WORKDIR}/evremote2.sh ${D}${sysconfdir}/init.d
    ln -sf ../init.d/evremote2.sh ${D}${sysconfdir}/rcS.d/S40evremote2
}

FILES_${PN}-dev += "${includedir}/mmeimage"

