"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.InvitationStatusValidationPipe = void 0;
const common_1 = require("@nestjs/common");
const invitation_entity_1 = require("../Entity/invitation.entity");
class InvitationStatusValidationPipe {
    constructor() {
        this.allowedStatus = [invitation_entity_1.InvitationStatus.upComing, invitation_entity_1.InvitationStatus.celebrated];
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
exports.InvitationStatusValidationPipe = InvitationStatusValidationPipe;
//# sourceMappingURL=invitationAvailabilityValidation.pipe.js.map