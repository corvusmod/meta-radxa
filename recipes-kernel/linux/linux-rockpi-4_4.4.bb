DESCRIPTION = "Linux kernel for RockPi-4"

require recipes-kernel/linux/linux-yocto.inc

DEPENDS += "openssl-native"

SRC_URI = " \
	git://github.com/radxa/kernel.git;branch=release-4.4-rockpi4; \
"

SRCREV = "a14f6502e0454a51626e3906f59637ab264bf53e"
LINUX_VERSION = "4.4.154"

# Override local version in order to use the one generated by linux build system
# And not "yocto-standard"
LINUX_VERSION_EXTENSION = ""
PR = "r1"
PV = "${LINUX_VERSION}"

# Include only supported boards for now
COMPATIBLE_MACHINE = "(rk3036|rk3066|rk3288|rk3328|rk3399|rk3308)"
deltask kernel_configme

do_compile_append() {
	oe_runmake dtbs
}

do_deploy_append() {
	mkdir -p ${DEPLOYDIR}/overlays
	cp -rf ${WORKDIR}/linux-rockpi_4_rk3399-standard-build/arch/arm64/boot/dts/rockchip/overlays-rockpi4/console-on-ttyS2.dtbo ${DEPLOYDIR}/overlays
	cp -rf ${WORKDIR}/linux-rockpi_4_rk3399-standard-build/arch/arm64/boot/dts/rockchip/overlays-rockpi4/console-on-ttyS4.dtbo ${DEPLOYDIR}/overlays
	cp -rf ${S}/arch/arm64/boot/dts/rockchip/overlays-rockpi4/hw_intfc.conf ${DEPLOYDIR}/overlays
}
