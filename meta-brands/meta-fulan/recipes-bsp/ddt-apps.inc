HOMEPAGE = "https://github/Duckbox-Developers/apps"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = "fulan-dvb-modules"

SRCREV = "${AUTOREV}"
INC_PR = "r1"
PV = "0.1+git${SRCPV}"
PKGV = "0.1.+git${GITPKGV}"

SRC_URI = " \
    git://github.com/Duckbox-Developers/apps.git;protocol=git \
"

S = "${WORKDIR}/git/tools"

inherit autotools 

EXTRA_OECONF = "--enable-silent-rules --enable-maintainer-mode --prefix="

LDFLAGS += "-lpthread -lrt"

do_configure() {
    cd ${S}
    ./autogen.sh
    oe_runconf
}

do_compile() {
    cd ${S}
    oe_runmake  'DESTDIR=${D}' 'SUBDIRS=${@d.getVar('PN',1).replace('-', '_')}' 
}

do_install() {
    cd ${S}
    oe_runmake  'DESTDIR=${D}' 'SUBDIRS=${@d.getVar('PN',1).replace('-', '_')}' install
}

