/**
 * Grunt configuration file
 *
 * @param grunt
 * @author jntakpe
 */
module.exports = function (grunt) {
    'use strict';
    grunt.util.linefeed = '\n';

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        bowerrc: grunt.file.readJSON('.bowerrc'),
        dirs: {
            bowerRes: '<%= bowerrc.directory %>',
            source: 'src/main/webapp/static',
            target: 'src/main/webapp/dist'
        },
        bower: {
            install: {
                options: {
                    targetDir: '<%= dirs.bowerRes %>',
                    cleanTargetDir: true,
                    layout: 'byComponent',
                    install: true,
                    copy: false,
                    verbose: true
                }
            }
        },
        concat: {
            jsThirdParty: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Core JS - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/jquery/dist/jquery.min.js',
                    '<%= dirs.bowerRes %>/bootstrap/dist/js/bootstrap.min.js',
                    '<%= dirs.bowerRes %>/angular/angular.min.js',
                    '<%= dirs.bowerRes %>/angular-messages/angular-messages.min.js',
                    '<%= dirs.bowerRes %>/angular-resource/angular-resource.min.js',
                    '<%= dirs.bowerRes %>/angular-ui-router/release/angular-ui-router.min.js',
                    '<%= dirs.bowerRes %>/angular-ui-utils/ui-utils.min.js',
                    '<%= dirs.bowerRes %>/angular-bootstrap/ui-bootstrap-tpls.min.js',
                    '<%= dirs.bowerRes %>/moment/min/moment-with-locales.js',
                    '<%= dirs.bowerRes %>/perfect-scrollbar/js/min/perfect-scrollbar.jquery.min.js'
                ],
                dest: '<%= dirs.target %>/js/third-party.min.js'
            },
            coreJs: {
                src: [
                    '<%= dirs.source %>/js/mfm.js',
                    '<%= dirs.source %>/js/mfm.directive.js',
                    '<%= dirs.source %>/js/mfm.service.js',
                    '<%= dirs.source %>/js/mfm.constant.js',
                    '<%= dirs.source %>/js/header/header.js',
                    '<%= dirs.source %>/js/header/header.controller.js',
                    '<%= dirs.source %>/js/home/home.js',
                    '<%= dirs.source %>/js/home/home.controller.js',
                    '<%= dirs.source %>/js/home/home.service.js',
                    '<%= dirs.source %>/js/admin/admin.js',
                    '<%= dirs.source %>/js/admin/docs/docs.js',
                    '<%= dirs.source %>/js/admin/metrics/metrics.js',
                    '<%= dirs.source %>/js/admin/metrics/metrics.controller.js',
                    '<%= dirs.source %>/js/admin/metrics/metrics.service.js',
                    '<%= dirs.source %>/js/applications/application.js',
                    '<%= dirs.source %>/js/applications/application.controller.js',
                    '<%= dirs.source %>/js/applications/application.service.js',
                    '<%= dirs.source %>/js/applications/dashboard/dashboard.js',
                    '<%= dirs.source %>/js/applications/dashboard/dashboard.controller.js',
                    '<%= dirs.source %>/js/applications/controllers/controllers.js',
                    '<%= dirs.source %>/js/applications/controllers/controllers.controller.js',
                    '<%= dirs.source %>/js/applications/properties/properties.js',
                    '<%= dirs.source %>/js/applications/properties/properties.controller.js',
                    '<%= dirs.source %>/js/applications/properties/properties.service.js',
                    '<%= dirs.source %>/js/applications/logs/logs.js',
                    '<%= dirs.source %>/js/applications/logs/logs.controller.js',
                    '<%= dirs.source %>/js/applications/logs/logs.service.js',
                    '<%= dirs.source %>/js/applications/health/health.js',
                    '<%= dirs.source %>/js/applications/health/health.controller.js',
                    '<%= dirs.source %>/js/partners/partner.js',
                    '<%= dirs.source %>/js/partners/partner.controller.js',
                    '<%= dirs.source %>/js/partners/partner.service.js',
                    '<%= dirs.source %>/js/environment/environment.js',
                    '<%= dirs.source %>/js/environment/environment.service.js',
                    '<%= dirs.source %>/js/environment/environment.controller.js'
                ],
                dest: '<%= dirs.target %>/js/core.js'
            },
            swaggerCss: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Swagger CSS dependencies - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/swagger-ui/dist/css/reset.css',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/css/screen.css'
                ],
                dest: '<%= dirs.target %>/css/swagger-bundle.min.css'
            },
            swaggerJs: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Swagger JS dependencies - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/shred.bundle.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/jquery-1.8.0.min.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/jquery.slideto.min.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/jquery.wiggle.min.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/jquery.ba-bbq.min.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/handlebars-1.0.0.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/underscore-min.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/backbone-min.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/swagger.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/swagger-ui.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/highlight.7.3.pack.js',
                    '<%= dirs.bowerRes %>/swagger-ui/dist/lib/swagger-oauth.js'
                ],
                dest: '<%= dirs.target %>/js/swagger-bundle.min.js'
            },
            cssThirdParty: {
                options: {
                    stripBanners: {
                        block: true
                    },
                    banner: '/*! <%= pkg.name %> - v<%= pkg.version %> - Third party CSS - ' +
                    '<%= grunt.template.today("yyyy-mm-dd") %> */\n'
                },
                src: [
                    '<%= dirs.bowerRes %>/bootstrap/dist/css/bootstrap.min.css',
                    '<%= dirs.bowerRes %>/font-awesome/css/font-awesome.min.css',
                    '<%= dirs.bowerRes %>/perfect-scrollbar/css/perfect-scrollbar.min.css'
                ],
                dest: '<%= dirs.target %>/css/third-party.min.css'
            }
        },
        cssmin: {
            coreCss: {
                files: [{
                    '<%= dirs.target %>/css/core.min.css': ['<%= dirs.source %>/css/mfm.css']
                }],
                options: {
                    shorthandCompating: false,
                    roundingPrecision: -1
                }
            },
            unify: {
                files: [{
                    '<%= dirs.target %>/css/unify.min.css': [
                        '<%= dirs.source %>/css/unify/style.css',
                        '<%= dirs.source %>/css/unify/app.css',
                        '<%= dirs.source %>/css/unify/blocks.css',
                        '<%= dirs.source %>/css/unify/header.css',
                        '<%= dirs.source %>/css/unify/footer.css',
                        '<%= dirs.source %>/css/unify/profile.css',
                        '<%= dirs.source %>/css/unify/sky-forms.css',
                        '<%= dirs.source %>/css/unify/custom-sky-forms.css'
                    ]
                }],
                options: {
                    shorthandCompating: false,
                    roundingPrecision: -1
                }
            }
        },
        copy: {
            fonts: {
                files: [
                    {
                        expand: true,
                        src: [
                            '<%= dirs.bowerRes %>/font-awesome/fonts/**',
                            '<%= dirs.bowerRes %>/bootstrap/fonts/**'
                        ],
                        dest: '<%= dirs.target %>/fonts',
                        filter: 'isFile',
                        flatten: true
                    }
                ]
            },
            img: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= dirs.source %>/img/',
                        src: [
                            '**'
                        ],
                        dest: '<%= dirs.target %>/img',
                        filter: 'isFile'
                    }
                ]
            }
        },
        watch: {
            js: {
                files: '<%= dirs.source %>/js/**/*.js',
                tasks: ['concat:coreJs']
            },
            css: {
                files: '<%= dirs.source %>/css/*.css',
                tasks: ['cssmin:coreCss']
            }
        },
        clean: ['<%= dirs.target %>/'],
        removeLoggingCalls: {
            files: ['<%= dirs.source %>/js/**.js'],
            options: {
                methods: ['log'],
                strategy: function (consoleStatement) {
                    return '';
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-bower-task');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-remove-logging-calls');

    grunt.registerTask('default', [
        'clean',
        'removeLoggingCalls',
        'concat:jsThirdParty',
        'concat:cssThirdParty',
        'concat:coreJs',
        'cssmin:coreCss',
        'cssmin:unify',
        'copy:fonts',
        'copy:img',
        'concat:swaggerCss',
        'concat:swaggerJs'
    ]);
};