import styled from 'styled-components/macro';

export const TreeTitle = styled.div`
  display: flex;
  align-items: center;

  h4 {
    flex: 1;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .action {
    display: none;
    flex-shrink: 0;
    width: 32px;
    height: 32px;
    line-height: 32px;
    text-align: center;
  }

  &:hover {
    .action {
      display: block;
    }
  }
`;
