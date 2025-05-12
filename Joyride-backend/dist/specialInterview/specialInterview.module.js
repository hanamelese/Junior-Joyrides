"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.SpecialInterviewModule = void 0;
const common_1 = require("@nestjs/common");
const typeorm_1 = require("@nestjs/typeorm");
const specialInterview_controller_1 = require("./specialInterview.controller");
const specialInterview_service_1 = require("./specialInterview.service");
const specialInterview_entity_1 = require("../Entity/specialInterview.entity");
let SpecialInterviewModule = class SpecialInterviewModule {
};
exports.SpecialInterviewModule = SpecialInterviewModule;
exports.SpecialInterviewModule = SpecialInterviewModule = __decorate([
    (0, common_1.Module)({
        imports: [
            typeorm_1.TypeOrmModule.forFeature([specialInterview_entity_1.SpecialInterviewEntity])
        ],
        controllers: [specialInterview_controller_1.SpecialInterviewController],
        providers: [specialInterview_service_1.SpecialInterviewService]
    })
], SpecialInterviewModule);
//# sourceMappingURL=specialInterview.module.js.map