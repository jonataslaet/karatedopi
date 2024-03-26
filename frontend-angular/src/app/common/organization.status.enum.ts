export const OrganizationStatusEnum = {
    ACTIVE: { id: 1, name: 'Ativo' },
    INACTIVE: { id: 2, name: 'Inativo' },
    SUSPENDED: {id: 3, name: "Suspenso"}
} as const;

export type OrganizationStatusEnum = keyof typeof OrganizationStatusEnum;
export type State = (typeof OrganizationStatusEnum)[OrganizationStatusEnum];