HOMEPAGE = "http://www.alumnit.ca/wiki/?WvDial"
DESCRIPTION = "WvDial is a program that makes it easy to connect your Linux workstation to the Internet."

require conf/license/license-gplv2.inc

inherit autotools

PR = "r3"

LICENSE = "GPL"

SRC_URI[md5sum] = "acd3b2050c9b65fff2aecda6576ee7bc"
SRC_URI[sha256sum] = "4fffab9652c760199c074533d1d3929bea55ab4233b11e735b0f1856d1ceec57"

SRC_URI = "\
	http://pkgs.fedoraproject.org/repo/pkgs/wvdial/wvdial-1.61.tar.gz/acd3b2050c9b65fff2aecda6576ee7bc/wvdial-1.61.tar.gz \
	file://wvdial_001.patch \
	"

DEPENDS = "wvstreams"
RDEPENDS_${PN} = "ppp"

do_compile () {
    make -j 1
}

do_install() {
    install -d ${D}/usr
    install -m 0755 wvdial wvdialconf ${D}/usr
}
