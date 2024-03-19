export const TournamentStatusEnum = {
    OPENED: { id: 1, name: 'Aberto' },
    IN_PROGRESS: { id: 2, name: 'Em andamento' },
    SUSPENDED: { id: 3, name: 'Suspenso' },
    FINISHED: { id: 4, name: 'Finalizado' },
} as const;

export type TournamentStatusEnum = keyof typeof TournamentStatusEnum;
export type State = (typeof TournamentStatusEnum)[TournamentStatusEnum];