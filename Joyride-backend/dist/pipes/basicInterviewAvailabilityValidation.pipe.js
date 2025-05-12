"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.BasicInterviewStatusValidationPipe = void 0;
const common_1 = require("@nestjs/common");
const basicInterview_entity_1 = require("../Entity/basicInterview.entity");
class BasicInterviewStatusValidationPipe {
    constructor() {
        this.allowedStatus = [basicInterview_entity_1.BasicInterviewStatus.upComing, basicInterview_entity_1.BasicInterviewStatus.hosted];
    }
    transform(value, metadata) {
        value = value?.toUpperCase();
        if (!this.isStatusValid(value)) {
            throw new common_1.BadRequestException(`${value} is an invalid availability status`);
        }
        return value;
    }
    isStatusValid(status) {
        const index = this.allowedStatus.indexOf(status);
        return index !== -1;
    }
}
exports.BasicInterviewStatusValidationPipe = BasicInterviewStatusValidationPipe;
//# sourceMappingURL=basicInterviewAvailabilityValidation.pipe.js.map