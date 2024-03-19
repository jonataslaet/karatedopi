export const OrganizationStatusEnum = {
    ACTIVE: { id: 1, name: 'Ativo' },
    INACTIVE: { id: 2, name: 'Inativo' },
} as const;

export type OrganizationStatusEnum = keyof typeof OrganizationStatusEnum;
export type State = (typeof OrganizationStatusEnum)[OrganizationStatusEnum];